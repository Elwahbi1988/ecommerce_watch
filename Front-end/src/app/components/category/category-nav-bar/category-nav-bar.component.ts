import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActionCategoryEvent, AppCategoryDataState, CategoryActionTypes } from '../../../state/category.state';
import { Observable } from 'rxjs';
import { Category } from 'src/app/model/category.model';

@Component({
  selector: 'app-category-nav-bar',
  templateUrl: './category-nav-bar.component.html',
  styleUrls: ['./category-nav-bar.component.css']
})
export class CategoryNavBarComponent implements OnInit {



  @Input() category$: Observable<AppCategoryDataState<Category[]>> | null = null;
 @Output() categoryEventEmitter : EventEmitter<ActionCategoryEvent> = new EventEmitter();
    emitCategoryEvent(type: CategoryActionTypes, payload?: any) {
  this.categoryEventEmitter.emit({ type, payload });
}

 constructor(){}

ngOnInit(): void {
}

onNewCategory() {
  this.emitCategoryEvent(CategoryActionTypes.NEW_CATEGORY);
  console.log(this.emitCategoryEvent(CategoryActionTypes.NEW_CATEGORY))
  }
onGetAllCategory() {
this.emitCategoryEvent(CategoryActionTypes.GET_ALL_CATEGORY);
}
onSearchCategory(dataForm:any) {
  this.emitCategoryEvent(CategoryActionTypes.SEARCH_CATEGORY, dataForm);
}
}
