package praktikum.unit;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import praktikum.Bun;
import praktikum.Burger;
import praktikum.Ingredient;
import praktikum.IngredientType;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@Epic("Бургер")
@Feature("Работа с бургером")
public class BurgerTest {

    private Burger burger;
    private Bun bun;
    private Ingredient ingredient;

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
    @Test
    @Story("Установка булки")
    @Description("Проверяем, что после установки булки в бургер объект bun обновляется корректно")
    @DisplayName("Тест установки булки")
    public void testSetBuns() {
        Bun anotherBun = mock(Bun.class);
        burger.setBuns(anotherBun);
        assertSame(anotherBun, burger.bun);
    }

    @Test
    @Story("Добавление ингредиента")
    @Description("Проверяем, что добавление ингредиента помещает его в список ингредиентов")
    @DisplayName("Тест добавления ингредиента")
    public void testAddIngredient() {
        Ingredient sauce = mock(Ingredient.class);
        burger.addIngredient(sauce);
        assertTrue( burger.ingredients.contains(sauce));
    }

    @Test
    @Story("Удаление ингредиента")
    @Description("Проверяем, что удаление ингредиента очищает список ингредиентов")
    @DisplayName("Тест удаления ингредиента")
    public void testRemoveIngredient() {
        burger.removeIngredient(0);
        assertTrue(burger.ingredients.isEmpty());
    }

    @Test
    @Story("Расчёт цены")
    @Description("Проверяем, что метод getPrice возвращает корректную сумму цены булок и ингредиентов")
    @DisplayName("Тест расчёта цены бургера")
    public void testGetPrice() {
        float expectedPrice = bun.getPrice() * 2 + ingredient.getPrice();
        assertEquals(expectedPrice, burger.getPrice(), 0.001);
    }

    @Test
    @Story("Формирование рецепта")
    @Description("Проверяем, что метод getReceipt формирует строку рецепта полностью корректно")
    @DisplayName("Тест рецепта бургера")
    public void testGetReceipt() {
        String expected = String.format(
                "(==== %s ====)%n" +
                        "= %s %s =%n" +
                        "(==== %s ====)%n%n" +
                        "Price: %f%n",
                bun.getName(),
                ingredient.getType().toString().toLowerCase(),
                ingredient.getName(),
                bun.getName(),
                burger.getPrice()
        );

        String actual = burger.getReceipt();
        assertEquals(expected, actual);
    }
}


