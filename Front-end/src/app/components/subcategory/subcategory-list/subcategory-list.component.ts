import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { Subcategory } from 'src/app/model/subcategory.model';
import { ActionSubcategoryEvent, AppSubcategoryDataState, DataStateSubcategoryEnum, SubcategoryActionTypes } from 'src/app/state/subcategory.state';

@Component({
  selector: 'app-subcategory-list',
  templateUrl: './subcategory-list.component.html',
  styleUrls: ['./subcategory-list.component.css']
})
export class SubcategoryListComponent {
  @Input() subcategory$: Observable<AppSubcategoryDataState<Subcategory[]>> | null = null;
  @Output() subcategoryEventEmitter:EventEmitter<ActionSubcategoryEvent> =new EventEmitter<ActionSubcategoryEvent>();

  readonly DataStateSubcategoryEnum = DataStateSubcategoryEnum;

  constructor(){}

  ngOnInit(): void {
  }
  editSubcategory(sub: Subcategory) {
    this.subcategoryEventEmitter.emit(
      {type: SubcategoryActionTypes.EDIT_SUBCATEGORY,payload:sub});
    }
    onDeleteSubcategory(sub: Subcategory) {
      this.subcategoryEventEmitter.emit(
        {type: SubcategoryActionTypes.DELETE_SUBCATEGORY,payload:sub});
    }
    onSelectSubcategory(sub: Subcategory) {
      this.subcategoryEventEmitter.emit(
        {type: SubcategoryActionTypes.SELECT_SUBCATEGORY,payload:sub});
    }
  }
