import { Component, OnInit } from '@angular/core';
import {CatFood} from "../cat-food.model";
import {CatFoodService} from "../cat-food.service";
import {CatsService} from "../cats.service";
import {FoodService} from "../food.service";
import {Cat} from "../cat.model";
import {Food} from "../food.model";

@Component({
  selector: 'app-cat-food',
  templateUrl: './cat-food.component.html',
  styleUrls: ['./cat-food.component.css']
})
export class CatFoodComponent implements OnInit {

  catFoods: CatFood[];
  selectedCatFood?: CatFood;

  cats: Cat[];
  food: Food[];
  selectedCat?: Cat;
  selectedFood?: Food;

  addCatFood() {
    this.catFoodService.add({cat: this.selectedCat, food: this.selectedFood} as CatFood).subscribe(
      _ => this.getCatFood()
    )
  }

  onSelectCat(cat: Cat) {
    this.selectedCat = cat;
  }

  onSelectFood(food: Food) {
    this.selectedFood = food;
  }

  onSelect(catFood: CatFood) {
    this.selectedCatFood = catFood;
  }

  delete(catFood: CatFood) {
    this.catFoodService.delete(catFood.cat.id, catFood.food.id).subscribe(
      _ => this.getCatFood()
    )
  }

  getCatFood() {
    this.catFoodService.getCatFoods().subscribe(
      catFoods => {this.catFoods = catFoods.catFoods; console.log(catFoods)}
    )
  }

  getCats() {
    this.catService.getCats().subscribe(
      cats => this.cats = cats.cats
    )
  }

  getFood() {
    this.foodService.getFood().subscribe(
      food => this.food = food.foods
    )
  }
  constructor(private catFoodService: CatFoodService,
              private catService: CatsService,
              private foodService: FoodService) { }

  ngOnInit(): void {
    this.getCatFood();
    this.getCats();
    this.getFood();
  }

}
