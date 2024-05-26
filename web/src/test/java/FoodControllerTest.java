import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.domain.Food;
import core.service.FoodService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import web.controller.FoodController;
import web.converter.FoodConverter;
import web.dto.CatDTO;
import web.dto.FoodDTO;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FoodControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private FoodController foodController;

    @Mock
    private FoodService foodService;

    @Mock
    private FoodConverter foodConverter;

    private Food food1, food2;
    private FoodDTO foodDTO1, foodDTO2;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(foodController)
                .build();
        initData();
    }

    private FoodDTO createFoodDto(Food food) {
        FoodDTO foodDTO = FoodDTO.builder()
                .name(food.getName())
                .producer(food.getProducer())
                .expirationDate(food.getExpirationDate())
                .build();
        foodDTO.setId(food.getId());
        return foodDTO;
    }
    private void initData() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.getActualMinimum(Calendar.YEAR), Calendar.JANUARY, 1);
        food1 = new Food(0L, "f1", "p1", calendar.getTime());
        calendar.set(calendar.getActualMaximum(Calendar.YEAR), Calendar.JANUARY, 1);
        food2 = new Food(1L, "f2", "p2", calendar.getTime());

        foodDTO1 = createFoodDto(food1);
        foodDTO2 = createFoodDto(food2);
    }

    private String toJsonString(FoodDTO foodDTO) {
        try {
            return new ObjectMapper().writeValueAsString(foodDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getFoodFromRepository() throws Exception {
        List<Food> foods = Arrays.asList(food1, food2);
        Set<FoodDTO> foodDTOSet = new HashSet<>(Arrays.asList(foodDTO1, foodDTO2));
        when(foodService.getFoodFromRepository()).thenReturn(foods);
        when(foodConverter.convertModelsToDTOs(foods)).thenReturn(foodDTOSet);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/food")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.foods", hasSize(2)))
                .andExpect(jsonPath("$.foods[0].name", anyOf(is("f1"), is("f2"))))
                .andExpect(jsonPath("$.foods[0].producer", anyOf(is("p1"), is("p2"))));

        verify(foodService,times(1)).getFoodFromRepository();
        verify(foodConverter, times(1)).convertModelsToDTOs(foods);
        verifyNoMoreInteractions(foodService, foodConverter);
    }

    @Test
    public void getNotExpiredFood() throws Exception {
        List<Food> foods = Collections.singletonList(food2);
        Set<FoodDTO> foodDTOSet = new HashSet<>(Collections.singletonList(foodDTO2));
        when(foodService.getNotExpiredFood()).thenReturn(foods);
        when(foodConverter.convertModelsToDTOs(foods)).thenReturn(foodDTOSet);
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/food/valid")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.foods", hasSize(1)))
                .andExpect(jsonPath("$.foods[0].name", anyOf(is("f2"))))
                .andExpect(jsonPath("$.foods[0].producer", anyOf(is("p2"))));

        verify(foodService,times(1)).getNotExpiredFood();
        verify(foodConverter, times(1)).convertModelsToDTOs(foods);
        verifyNoMoreInteractions(foodService, foodConverter);
    }

}
