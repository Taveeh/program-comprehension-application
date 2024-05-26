import {Component, OnInit} from '@angular/core';
import {Toy} from "../cat-toy.model";
import {ToyService} from "../toy.service";
import {CatsService} from "../cats.service";
import {Cat} from "../cat.model";

@Component({
  selector: 'app-toy',
  templateUrl: './toy.component.html',
  styleUrls: ['./toy.component.css']
})
export class ToyComponent implements OnInit {

  selectedToy?: Toy;
  toys: Toy[];
  cats: Cat[];
  selectedCat?: Cat;

  constructor(private toyService: ToyService, private catsService: CatsService) {
  }

  getToys() {
    this.toyService.getToys()
      .subscribe(toys => {
          this.toys = toys.toys
        }
      )
  }

  getCats() {
    this.catsService.getCats()
      .subscribe(cats => this.cats = cats.cats);
  }

  add(name: string, size: string) {
    this.toyService.add({name, size: Number(size)} as Toy)
      .subscribe(_ => this.getToys())
  }

  delete(toy: Toy) {

  }

  onSelectCat(cat: Cat) {
    this.selectedCat = cat;
  }

  onSelect(toy: Toy) {
    this.selectedToy = toy;
  }

  assignToyToCat() {
    this.toyService.assignToyToCat(this.selectedToy, this.selectedCat).subscribe()
  }

  ngOnInit(): void {
    this.getToys();
    this.getCats();
  }

}
