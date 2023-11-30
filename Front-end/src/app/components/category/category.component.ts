import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Category } from '../../model/category.model';
import { CategoryService } from '../../services/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ActionCategoryEvent, AppCategoryDataState, DataStateCategoryEnum } from 'src/app/state/category.state';
import { CategoryActionTypes } from '../../state/category.state';
import { Observable, catchError, map, of, startWith } from 'rxjs';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit{


  @Output() categoryEventEmitter : EventEmitter<ActionCategoryEvent> = new EventEmitter();
  category$: Observable<AppCategoryDataState<Category[]>> | null = null;
  readonly DataStateCategoryEnum = DataStateCategoryEnum;
category:any;
  constructor(private categoryService: CategoryService, private router:Router, private route: ActivatedRoute) {}



  ngOnInit(): void {}

  onGetAllCategory() {
    this.category$ = this.categoryService.getAllCategory().pipe(
      map(data => ({ dataState: DataStateCategoryEnum.LOADED, data: data.data })),
      startWith({ dataState: DataStateCategoryEnum.LOADING }),
      catchError(err => of({ dataState: DataStateCategoryEnum.ERROR, errorMessage: err.message }))
    );
  }

  onGetSelectedCategory() {
    this.category$ = this.categoryService.getSelectCategory().pipe(
      map(data => ({ dataState: DataStateCategoryEnum.LOADED, data: data })),
      startWith({ dataState: DataStateCategoryEnum.LOADING }),
      catchError(err => of({ dataState: DataStateCategoryEnum.ERROR, errorMessage: err.message }))
    );
  }

  onGetAvailableCategory() {
    this.category$ = this.categoryService.getAvailableCategory().pipe(
      map(data => ({ dataState: DataStateCategoryEnum.LOADED, data: data })),
      startWith({ dataState: DataStateCategoryEnum.LOADING }),
      catchError(err => of({ dataState: DataStateCategoryEnum.ERROR, errorMessage: err.message }))
    );
  }

  onSearchCategory(dataForm: any) {
    this.category$ = this.categoryService.SearchCategory(dataForm.keyword).pipe(
      map(data => ({ dataState: DataStateCategoryEnum.LOADED, data: data.data })),
      startWith({ dataState: DataStateCategoryEnum.LOADING }),
      catchError(err => of({ dataState: DataStateCategoryEnum.ERROR, errorMessage: err.message }))
    );
  }

  onSelectCategory(cat: Category) {
    this.categoryService.selectCategory(cat).subscribe(data => {
      cat.selected = data.selected;
    });
  }

  onDeleteCategory(cat: Category) {
    let v = confirm("Are you sure you want to delete this?");
    if (v === true) {
      this.categoryService.deleteCategory(cat).subscribe(() => {
        this.onGetAllCategory();
      });
    }
  }
  onNewCategory(){
    this.router.navigateByUrl("/newCategory");
  }
  //onEditProduct(p: Product){
    //this.router.navigateByUrl("EditProduct");
  //}


editCategory(cat: any) {
  console.log(cat);
  this.router.navigateByUrl("/update-category/" + cat.id);
}


onActionCategoryEvent($event: ActionCategoryEvent) {
 switch($event.type){
  case CategoryActionTypes.GET_ALL_CATEGORY: this.onGetAllCategory(); break;
  case CategoryActionTypes.GET_SELECTED_CATEGORY: this.onGetSelectedCategory(); break;
  case CategoryActionTypes.GET_AVAILABLE_CATEGORY: this.onGetAvailableCategory(); break;
  case CategoryActionTypes.SEARCH_CATEGORY: this.onSearchCategory($event.payload); break;
  case CategoryActionTypes.EDIT_CATEGORY: this.editCategory($event.payload); break;
  case CategoryActionTypes.SELECT_CATEGORY: this.onSelectCategory($event.payload); break;
  case CategoryActionTypes.NEW_CATEGORY: this.onNewCategory(); break;
  case CategoryActionTypes.DELETE_CATEGORY: this.onDeleteCategory($event.payload); break;
 }
  }
}
