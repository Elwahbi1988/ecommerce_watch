import { CategoryComponent } from './../components/category/category.component';

// category.state.ts
export enum CategoryActionTypes{
  GET_ALL_CATEGORY="[Category] Get All category",
  GET_SELECTED_CATEGORY="[Category] Get Selected category",
  GET_AVAILABLE_CATEGORY="[Category] Get Available category",
  SEARCH_CATEGORY="[Category] Search category",
  NEW_CATEGORY="[Category] New category",
  SELECT_CATEGORY="[Category] Select category",
  EDIT_CATEGORY="[Category] Edit category",
  DELETE_CATEGORY="[Category] Delete category",
}

export interface ActionCategoryEvent{
  type?:CategoryActionTypes,
  payload?:any,
}
export enum DataStateCategoryEnum {
  LOADING,
  LOADED,
  ERROR
}

export interface AppCategoryDataState<T> {
  dataState: DataStateCategoryEnum;
  data?: T;
  errorMessage?: string;
}
