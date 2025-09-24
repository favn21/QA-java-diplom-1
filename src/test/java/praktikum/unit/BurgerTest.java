package praktikum.unit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class BurgerTest {

    private Burger burger;
    private Bun bun;
    private Ingredient ingredient;

    private final int oldIndex;
    private final int newIndex;

    public BurgerTest(int oldIndex, int newIndex) {
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
    }

    @Before
    public void setUp() {
        burger = new Burger();
        bun = mock(Bun.class);
        ingredient = mock(Ingredient.class);

        when(bun.getName()).thenReturn("Black Bun");
        when(bun.getPrice()).thenReturn(100f);

        when(ingredient.getName()).thenReturn("Cheese");
        when(ingredient.getPrice()).thenReturn(50f);
        when(ingredient.getType()).thenReturn(IngredientType.FILLING);

        burger.setBuns(bun);
        burger.addIngredient(ingredient);
    }

    @Parameterized.Parameters(name = "Переместить ингредиент {0} -> {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, 0},
                {0, 1},
        });
    }

    @Test
    public void testSetBuns() {
        Bun anotherBun = mock(Bun.class);
        burger.setBuns(anotherBun);
        assertSame(anotherBun, burger.bun);
    }

    @Test
    public void testAddIngredient() {
        Ingredient sauce = mock(Ingredient.class);
        burger.addIngredient(sauce);
        assertTrue( burger.ingredients.contains(sauce));
    }

    @Test
    public void testRemoveIngredient() {
        burger.removeIngredient(0);
        assertTrue(burger.ingredients.isEmpty());
    }

    @Test
    public void testMoveIngredient() {
        Ingredient another = mock(Ingredient.class);
        when(another.getName()).thenReturn("Sauce");
        when(another.getPrice()).thenReturn(20f);
        when(another.getType()).thenReturn(IngredientType.SAUCE);

        burger.addIngredient(another);
        burger.moveIngredient(oldIndex, newIndex);

        assertEquals(2, burger.ingredients.size());
    }

    @Test
    public void testGetPrice() {
        float expectedPrice = bun.getPrice() * 2 + ingredient.getPrice();
        assertEquals(expectedPrice, burger.getPrice(), 0.001);
    }

    @Test
    public void testGetReceipt() {
        String receipt = burger.getReceipt();

        assertTrue(receipt.contains(bun.getName()));
        assertTrue(receipt.contains(ingredient.getName()));

        String expectedPriceLine = String.format("Price: %f", burger.getPrice());
        assertTrue(receipt.contains(expectedPriceLine));
    }
}

