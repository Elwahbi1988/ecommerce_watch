export interface Product{
  _id : number;
  sku: number;
  productImage:string;
  productName: string;
  subCategoryId: number,
  subCategoryName: String,
  shortDescription: string;
  longDescription: String;
  price:number;
  quantity: number;
  discountPrice: number;
  active: boolean;
  selected: boolean;
}
