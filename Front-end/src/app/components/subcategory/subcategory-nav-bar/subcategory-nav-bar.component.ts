import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { Subcategory } from 'src/app/model/subcategory.model';
import { ActionSubcategoryEvent, AppSubcategoryDataState, SubcategoryActionTypes } from 'src/app/state/subcategory.state';

@Component({
  selector: 'app-subcategory-nav-bar',
  templateUrl: './subcategory-nav-bar.component.html',
  styleUrls: ['./subcategory-nav-bar.component.css']
})
export class SubcategoryNavBarComponent {


  @Input() subcategory$: Observable<AppSubcategoryDataState<Subcategory[]>> | null = null;
 @Output() subcategoryEventEmitter : EventEmitter<ActionSubcategoryEvent> = new EventEmitter();
    emitSubcategoryEvent(type: SubcategoryActionTypes, payload?: any) {
  this.subcategoryEventEmitter.emit({ type, payload });
}

 constructor(){}

ngOnInit(): void {
}

onNewSubcategory() {
  this.emitSubcategoryEvent(SubcategoryActionTypes.NEW_SUBCATEGORY);
  console.log(this.emitSubcategoryEvent(SubcategoryActionTypes.NEW_SUBCATEGORY))
  }
onGetAllSubcategory() {
this.emitSubcategoryEvent(SubcategoryActionTypes.GET_ALL_SUBCATEGORY);
}
onGetSelectedSubcategory() {
  this.emitSubcategoryEvent(SubcategoryActionTypes.GET_SELECTED_SUBCATEGORY);
}

onGetAvailableSubcategory() {
  this.emitSubcategoryEvent(SubcategoryActionTypes.GET_AVAILABLE_SUBCATEGORY);
}
onSearchSubcategory(dataForm:any) {
  this.emitSubcategoryEvent(SubcategoryActionTypes.SEARCH_SUBCATEGORY, dataForm);
}
}
