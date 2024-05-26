export interface Toy {
  id: number;
  name: string;
  size: number;
}
export interface CatToy {
  id: number;
  name: string;
  breed: string;
  catYears: number;
  favoriteToy: Toy;
}
