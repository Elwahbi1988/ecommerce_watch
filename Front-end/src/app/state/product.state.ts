
// product.state.ts
export enum ProductActionTypes{
  GET_ALL_PRODUCT="[Product] Get All products",
  GET_SELECTED_PRODUCT="[Product] Get Selected products",
  GET_AVAILABLE_PRODUCT="[Product] Get Available products",
  SEARCH_PRODUCT="[Product] Search products",
  NEW_PRODUCT="[Product] New products",
  EDIT_PRODUCT="[Product] Edit products",
  DELETE_PRODUCT="[Product] Delete product",
  GET_ALL_CATEGORY="[Category] Get All category",
  GET_SELECTED_CATEGORY="[Category] Get Selected category",
  GET_AVAILABLE_CATEGORY="[Category] Get Available category",
  SEARCH_CATEGORY="[Category] Search category",
  NEW_CATEGORY="[Category] New category",
  SELECT_CATEGORY="[Category] Select category",
  EDIT_CATEGORY="[Category] Edit category",
  DELETE_CATEGORY="[Category] Delete category",
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
