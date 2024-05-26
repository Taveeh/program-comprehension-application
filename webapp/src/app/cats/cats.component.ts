import {Component, OnInit} from '@angular/core';
import {Cat} from "../cat.model";
import {CatsService} from "../cats.service";
import {Router} from "@angular/router";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-cats',
  templateUrl: './cats.component.html',
  styleUrls: ['./cats.component.css']
})
export class CatsComponent implements OnInit {
  cats: Array<Cat>;
  selectedCat: Cat;

  addCatForm = new FormGroup({
    catName: new FormControl('', Validators.required),
    catBreed: new FormControl('', Validators.required),
    catYears: new FormControl('', Validators.required),
    favoriteToyName: new FormControl('', Validators.required),
    favoriteToySize: new FormControl('', Validators.required)
  })

  addCatFormMethod(data) {
    let name = data.catName.trim();
    let breed = data.catBreed.trim();
    let age = data.catYears.trim();
    let toyName = data.favoriteToyName.trim();
    let toySize = data.favoriteToySize.trim();
    if (!name || !breed || !age) {
      alert("Please fill all fields");
      return;
    }
    this.catService.add({
      name: name,
      breed: breed,
      catYears: Number(age),
      favoriteToyName: toyName,
      favoriteToySize: Number(toySize)
    } as Cat)
      .subscribe(
        _ => this.getCats()
      )
  }

  onSubmit() {
    this.addCatFormMethod(this.addCatForm.value)
  }

  constructor(private catService: CatsService, private router: Router) {
  }

  onSelect(cat: Cat): void {
    this.selectedCat = cat;
  }

  goToDetail(): void {
    this.router.navigate(['/cat/detail', this.selectedCat.id]);
  }

  add(name: string, breed: string, age: string, ): void {
    name = name.trim();
    breed = breed.trim();
    age = age.trim();
    if (!name || !breed || !age) {
      return;
    }
    const catYears = Number(age.trim());
    this.catService.add({name, breed, catYears} as Cat)
      .subscribe(_ => this.getCats());

  }

  getCats(): void {
    this.catService.getCatsWithToys()
      .subscribe(cats => {
        console.log(cats);
        this.cats = cats.cats
        for (let i in this.cats) {
          if (cats.cats[i].favoriteToy != null) {
            this.cats[i].favoriteToyName = cats.cats[i].favoriteToy.name || null
            this.cats[i].favoriteToySize = cats.cats[i].favoriteToy.size || null
          }
        }
      });
    console.log(this.cats);
  }

  delete(cat: Cat): void {
    this.cats = this.cats.filter(c => c !== cat);
    this.catService.delete(cat.id).subscribe();
  }

  save(): void {
    this.catService.update(this.selectedCat).subscribe();
  }

  orderCatsByAge(): void {
    this.cats.sort((a: Cat, b: Cat) => a.catYears - b.catYears)
  }

  ngOnInit(): void {
    this.getCats();
  }

}
