import {Component, OnInit} from '@angular/core';
import {FoodService} from "../food.service";
import {Router} from "@angular/router";
import {Food} from "../food.model";
import {Cat} from "../cat.model";
import {CatFoodService} from "../cat-food.service";

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrls: ['./food.component.css']
})
export class FoodComponent implements OnInit {

  selectedFood?: Food;
  foodList: Food[];
  catsEatingSelectedFood: Cat[];

  constructor(private foodService: FoodService, private router: Router, private catFoodService: CatFoodService) {
  }

  onSelect(food: Food) {
    this.selectedFood = food;
    this.catFoodService.filterCatsThatEatCertainFood(food.id).subscribe(
      catFood => {
        console.log(catFood);
        this.catsEatingSelectedFood = catFood.catFoods.map(
          cf => cf.cat
        );
      }
    );

  }

  goToDetail() {
    this.router.navigate(['/food/detail', this.selectedFood.id])
  }

  getFood() {
    this.foodService.getFood().subscribe(
      food => {
        this.foodList = food.foods.map(
          f => f = {
            id: f.id,
            name: f.name,
            producer: f.producer,
            expirationDate: new Date(f.expirationDate)
          } as Food
        )
        console.log(this.foodList)
      }
    );
  }

  getValidFood() {
    this.foodService.getValidFood().subscribe(
      food => {
        this.foodList = food.foods.map(
          f => f = {
            id: f.id,
            name: f.name,
            producer: f.producer,
            expirationDate: new Date(f.expirationDate)
          } as Food
        )
      }
    )
  }

  add(name: string, producer: string, date: string) {
    name = name.trim();
    producer = producer.trim();
    console.log(name);
    console.log(producer);
    console.log(date);
    if (!name || !producer || !date) {
      alert("Please enter all the info for the food");
      return;
    }
    let expirationDate = new Date(date);
    this.foodService.add({name, producer, expirationDate} as Food)
      .subscribe(_ => this.getFood())
  }

  delete(food: Food) {
    this.foodService.delete(food.id).subscribe(_ => this.getFood())
  }

  save() {
    this.foodService.update(this.selectedFood).subscribe()
  }

  ngOnInit(): void {
    this.getFood();
  }

}
