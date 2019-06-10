package com.epam.split;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.junit.Test;

/**
 * This is a custom Spliterator capable to divide the Stream (list) of authors
 * if it the element in the middle has Author::relatedArticle == 0;
 *
 * This condition is voluntary and can be any depending on the case.
 */
public class SpliteratorsTestCustomExample1 {

    public class RelatedAuthorSpliterator implements Spliterator<Author> {

        private final List<Author> list;
        AtomicInteger current = new AtomicInteger(0);
        // standard constructor/getters

        public RelatedAuthorSpliterator(final List<Author> list) {
            this.list = list;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Author> action) {
            printThreadAndMethod("tryAdvance");
            action.accept(list.get(current.getAndIncrement()));
            return current.get() < list.size();
        }

        @Override
        public Spliterator<Author> trySplit() {
            printThreadAndMethod("trySplit");

            int currentSize = list.size() - current.get();
            if (currentSize < 2) {
                return null;
            }
            for (int splitPos = currentSize / 2 + current.intValue();
                 splitPos < list.size(); splitPos++) {
                if (list.get(splitPos).getRelatedArticleId() == 0) {
                    Spliterator<Author> spliterator
                            = new RelatedAuthorSpliterator(
                            list.subList(current.get(), splitPos));
                    current.set(splitPos);
                    printThreadAndMsg("Returned spliterator of Est Size: " + spliterator.estimateSize());
                    return spliterator;
                } else {
                    printThreadAndMsg("Returned null");
                }
            }
            return null;
        }

        @Override
        public long estimateSize() {
            printThreadAndMethod("estimatedSize: " + (list.size() - current.get()));
            return list.size() - current.get();
        }

        private void printThreadAndMethod(final String method) {
            System.out.println(Thread.currentThread().getName() + ": " + method);
        }

        private void printThreadAndMsg(final String msg) {
            System.out.println(Thread.currentThread().getName() + ": " + msg);
        }

        @Override public void forEachRemaining(final Consumer<? super Author> action) {
            printThreadAndMethod("forEachRemaining");
            do {
            } while (tryAdvance(action));
        }

        @Override
        public int characteristics() {
            printThreadAndMethod("characteristics");
            return CONCURRENT;
        }
    }

    @Test
    public void testCreateFromArraySingleThreadedNonSplitted() {
        List<Author> list = createAuthors();

        Map<Integer, List<Author>> authors = StreamSupport.stream(new RelatedAuthorSpliterator(list), true).
                collect(Collectors.groupingBy(Author::getRelatedArticleId));

        authors.entrySet().forEach(entry -> {
            System.out.println("ArticleID = " + entry.getKey() + " Authors: "
                    + entry.getValue().stream()
                    .map(Author::getName)
                    .collect(Collectors.joining(",")));
        });
    }


    @Test
    public void testCreateFromArraySpliteratorConcurrent() {
        List<Author> list = createAuthors();
        List<Author> bigList = multiplyAuthors(list, 2);

        System.out.println("Is parallel: " + StreamSupport.stream(new RelatedAuthorSpliterator(bigList), true).
                parallel().isParallel());

        Map<Integer, List<Author>> authors = StreamSupport.stream(new RelatedAuthorSpliterator(bigList), true).
                parallel()
                .collect(Collectors.groupingBy(Author::getRelatedArticleId));

        // ForJoinPool is optional
        authors.entrySet().forEach(entry -> {
            System.out.println("ArticleID = " + entry.getKey() + " Authors: "
                    + entry.getValue().stream()
                    .map(Author::getName)
                    .collect(Collectors.joining(",")));
        });
    }

    private List<Author> multiplyAuthors(final List<Author> list, final int multiply) {
        ArrayList<Author> newAuthors = new ArrayList<>();
        IntStream.range(0, multiply)
                .forEach(i -> {
                    newAuthors.addAll(list);
                });
        return newAuthors;
    }


    private List<Author> createAuthors() {
        List<Author> list = new ArrayList();
        list.add(new Author("A0", 0)); // TODO: remove these Authors with relatedArticleId == 0
        list.add(new Author("B0", 0)); // Then trySplit will never split the original spliterator
        list.add(new Author("C0", 0));

        list.add(new Author("A1", 1));
        list.add(new Author("B1", 1));
        list.add(new Author("C1", 1));

        list.add(new Author("A2", 2)); // TODO: replace it with 0 then it will be sub-splitted for estimatedSize = 7
        list.add(new Author("B2", 2));
        list.add(new Author("C2", 2));

        list.add(new Author("A3", 3));
        list.add(new Author("B3", 3));
        list.add(new Author("C3", 3));

        list.add(new Author("A4", 4));
        list.add(new Author("B4", 4));
        list.add(new Author("C4", 4));
        return list;
    }

    public static class Author {

        private String name;
        private int relatedArticleId;

        // standard getters, setters & constructors

        public Author(final String name, final int relatedArticleId) {
            this.name = name;
            this.relatedArticleId = relatedArticleId;
        }

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public int getRelatedArticleId() {
            return relatedArticleId;
        }

        public void setRelatedArticleId(final int relatedArticleId) {
            this.relatedArticleId = relatedArticleId;
        }
    }

}
