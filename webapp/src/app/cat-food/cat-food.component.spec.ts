import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CatFoodComponent } from './cat-food.component';

describe('CatFoodComponent', () => {
  let component: CatFoodComponent;
  let fixture: ComponentFixture<CatFoodComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CatFoodComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CatFoodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
