package com.epam.functions;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * TODO: Fix failing tests
 */
public class OptionalStreamsGroupingTests {

    @Test
    public void contextLoads() {
    }


    @Test(expected = NullPointerException.class)
    public void testNullPointerExcetion() {
        final String name = null;
        Optional<String> nameOptional = Optional.ofNullable(name); // TODO: fix in this line
        assertFalse(nameOptional.isPresent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullAble() throws Exception {
        final String name = null;
        Optional<String> nameOptional = Optional.ofNullable(name);
        nameOptional.orElseThrow(Exception::new);
        assertTrue(nameOptional.get() == null);
    }

    @Test
    public void getNotNull() {
        final String name = null;
        String nameOptional = Optional.ofNullable(name).orElse("Joe Doe");  // TODO: fix with orElse()
        assertTrue(Optional.ofNullable(nameOptional).isPresent()); //

        String brand = null;
        String result = Optional.ofNullable(brand).orElseGet(this::getDefuault);
        assertEquals(result, "a,D,I,D,A,S");
    }


    @Test
    public void filteringTestAkaStreams() {
        Item item = new Item(10);
        Item item2 = new Item(9);
        Item item3 = new Item(11);
        Item item4 = new Item(null);

        List<Item> items = Arrays.asList(
                item, item2, item3, item4
        );

        Collection<Item> result2 = items.stream().filter(this::filterInRangeOptional).collect(Collectors.toList());
        assertEquals(1, result2.size());

        // TODO: Add one more filter in a chain like
        // .filter(item_ -> {
        //     return Objects.nonNull(item_.price);
        //  })
        Collection<Item> result = items.stream().filter(this::filterInRange).collect(Collectors.toList());
        assertEquals(1, result.size());
    }

    class Product {
        String name;

        public Product(String name) {
            this.name = name;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }
    }

    @Test
    public void processUnwrapProducts() {
        // TODO: Fix in 1,2 or 3
        Collection<Product> products = Arrays.asList(new Product("Andy"), new Product(""), new Product("LG"));
        Collection<String> strs = products.stream()
                .map(Product::getName)
                .filter(Optional::isPresent) // 1
                .map(Optional::get) // 2
                .filter(item -> item.length() > 1) // 3
                .collect(Collectors.toList());


        assertEquals(1, strs.size());
    }

    private boolean filterInRange(Item item) {
        if (item == null)
            return false;
        if (item.price >= 11) return false;
        if (item.price <= 9) return false;
        if (item.price == 10) return true;

        return false;
    }

    private boolean filterInRangeOptional(Item item) {
        boolean result = Optional.ofNullable(item).
                map(Item::getPrice)
                .filter(price -> !(price >= 11))
                .filter(price -> !(price <= 9))
                .filter(price -> (price == 10))
                .isPresent();
        return result;
    }

    /**
     * Grouping with JsonObjects.
     * TODO: Fix in 1 and 2
     * TODO: remove one unnecessary line
     *
     */
    @Test
    public void grouping() {
        List<Item> items = Arrays.asList(
                new Item(1, RequestType.ONE),
                new Item(2, RequestType.ONE),
                new Item(3, RequestType.TWO),
                new Item(4, RequestType.TWO)
        );

        Map<RequestType, List<Item>> map = items.stream().collect(groupingBy(Item::getType));

        assertEquals(1, map.size()); // 1
        assertEquals(1, map.values().size()); // 2

        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        map.forEach((key, value) -> jsonObjectBuilder.add(key.name(), value.stream().map(Item::toString).collect(Collectors.joining(", "))));

        jsonObjectBuilder.add("Hello", "Dude");

        assertEquals("{\"TWO\":\"Item{price=3, type=TWO}, Item{price=4, type=TWO}\",\"ONE\":\"Item{price=1, type=ONE}, Item{price=2, type=ONE}\"}", jsonObjectBuilder.build().toString());
    }


    enum RequestType {
        ONE,
        TWO
    }

    class Item {
        Integer price;
        RequestType type;

        public Item(Integer price, RequestType type) {
            this.price = price;
            this.type = type;
        }

        public Item(Integer price) {
            this.price = price;
        }

        public Integer getPrice() {
            return price;
        }

        public RequestType getType() {
            return type;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "price=" + price +
                    ", type=" + type +
                    '}';
        }
    }

    private String getDefuault() {
        return "Adidas".chars().mapToObj(x -> {
            if (Character.isLowerCase(x))
                return (char)Character.toUpperCase(x);
            if (Character.isUpperCase(x))
                return (char)Character.toLowerCase(x);
            return null;
        }).map(String::valueOf).collect(Collectors.joining(", ")); // TODO: Fix here
    }

    private Integer culcNumber() {
        return 1;
    }

}
