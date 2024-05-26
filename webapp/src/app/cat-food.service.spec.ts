import { TestBed } from '@angular/core/testing';

import { CatFoodService } from './cat-food.service';

describe('CatFoodService', () => {
  let service: CatFoodService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CatFoodService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
