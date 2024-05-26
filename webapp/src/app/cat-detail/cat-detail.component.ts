import {Component, Input, OnInit} from '@angular/core';
import {CatsService} from "../cats.service";
import {ActivatedRoute, Params} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {Cat} from "../cat.model";
import {Location} from "@angular/common";

@Component({
  selector: 'app-cat-detail',
  templateUrl: './cat-detail.component.html',
  styleUrls: ['./cat-detail.component.css']
})
export class CatDetailComponent implements OnInit {
  @Input() cat?: Cat;
  constructor(private catService: CatsService, private route: ActivatedRoute, private location: Location) { }

  ngOnInit(): void {
    this.route.params.pipe(
      switchMap(
        (params: Params) => this.catService.getCat(+params['id'])
      )
    ).subscribe(cat => this.cat = cat);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.catService.update(this.cat).subscribe(_ => this.goBack());
  }
}
