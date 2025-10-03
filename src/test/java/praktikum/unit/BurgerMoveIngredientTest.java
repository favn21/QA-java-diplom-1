package praktikum.unit;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@Epic("Бургер")
@Feature("Перемещение ингредиентов")
@RunWith(Parameterized.class)
public class BurgerMoveIngredientTest {

    private Burger burger;
    private Bun bun;
    private Ingredient ingredient;

    private final int oldIndex;
    private final int newIndex;

    public BurgerMoveIngredientTest(int oldIndex, int newIndex) {
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
    @Story("Перемещаем ингредиент внутри списка")
    @Description("Проверяем, что размер списка ингредиентов корректен после перемещения ингредиента")
    @DisplayName("Тест перемещения ингредиента Burger")
    public void testMoveIngredient() {
        Ingredient another = mock(Ingredient.class);
        when(another.getName()).thenReturn("Sauce");
        when(another.getPrice()).thenReturn(20f);
        when(another.getType()).thenReturn(IngredientType.SAUCE);

        burger.addIngredient(another);
        burger.moveIngredient(oldIndex, newIndex);

        assertEquals(2, burger.ingredients.size());
    }
}

