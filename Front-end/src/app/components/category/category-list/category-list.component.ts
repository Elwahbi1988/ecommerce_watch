import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from 'src/app/model/category.model';
import { ActionCategoryEvent, AppCategoryDataState, CategoryActionTypes, DataStateCategoryEnum } from 'src/app/state/category.state';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  @Input() category$: Observable<AppCategoryDataState<Category[]>> | null = null;
  @Output() categoryEventEmitter:EventEmitter<ActionCategoryEvent> =new EventEmitter<ActionCategoryEvent>();

  readonly DataStateCategoryEnum = DataStateCategoryEnum;

  constructor(){}

  ngOnInit(): void {
  }
  editCategory(cat: Category) {
    this.categoryEventEmitter.emit(
      {type: CategoryActionTypes.EDIT_CATEGORY,payload:cat});
    }
    onDeleteCategory(cat: Category) {
      this.categoryEventEmitter.emit(
        {type: CategoryActionTypes.DELETE_CATEGORY,payload:cat});
      }
  }
