import { List } from "lodash";

export interface Category{
  id: number;
  categoryName: String;
  active: Boolean;
  SubCategory: any;
  selected: boolean;
}
