
// product.state.ts
export enum ProductActionTypes{
  GET_ALL_PRODUCT="[Product] Get All products",
  GET_SELECTED_PRODUCT="[Product] Get Selected products",
  GET_AVAILABLE_PRODUCT="[Product] Get Available products",
  SEARCH_PRODUCT="[Product] Search products",
  NEW_PRODUCT="[Product] New products",
  SELECT_PRODUCT="[Product] Select product",
  EDIT_PRODUCT="[Product] Edit product",
  DELETE_PRODUCT="[Product] Delete product",
}

export interface ActionEvent{
  type?:ProductActionTypes,
  payload?:any,
}
export enum DataStateEnum {
  LOADING,
  LOADED,
  ERROR
}

export interface AppDataState<T> {
  dataState: DataStateEnum;
  data?: T;
  errorMessage?: string;
}
