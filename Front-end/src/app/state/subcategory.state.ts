// category.state.ts
export enum SubcategoryActionTypes{
  GET_ALL_SUBCATEGORY="[Subcategory] Get All subcategory",
  GET_SELECTED_SUBCATEGORY="[Subcategory] Get Selected subcategory",
  GET_AVAILABLE_SUBCATEGORY="[Subcategory] Get Available subcategory",
  SEARCH_SUBCATEGORY="[Subcategory] Search subcategory",
  NEW_SUBCATEGORY="[Subcategory] New subcategory",
  SELECT_SUBCATEGORY="[Subcategory] Select subcategory",
  EDIT_SUBCATEGORY="[Subcategory] Edit subcategory",
  DELETE_SUBCATEGORY="[Subcategory] Delete subcategory",
}

export interface ActionSubcategoryEvent{
  type?:SubcategoryActionTypes,
  payload?:any,
}
export enum DataStateSubcategoryEnum {
  LOADING,
  LOADED,
  ERROR
}

export interface AppSubcategoryDataState<T> {
  dataState: DataStateSubcategoryEnum;
  data?: T;
  errorMessage?: string;
}
