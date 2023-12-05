import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, catchError, map, of, startWith } from 'rxjs';
import { Subcategory } from 'src/app/model/subcategory.model';
import { SubcategoryService } from 'src/app/services/subcategory.service';
import { ActionSubcategoryEvent, AppSubcategoryDataState, DataStateSubcategoryEnum, SubcategoryActionTypes } from 'src/app/state/subcategory.state';

@Component({
  selector: 'app-subcategory',
  templateUrl: './subcategory.component.html',
  styleUrls: ['./subcategory.component.css']
})
export class SubcategoryComponent implements OnInit {

  @Output() subcategoryEventEmitter : EventEmitter<ActionSubcategoryEvent> = new EventEmitter();
  subcategory$: Observable<AppSubcategoryDataState<Subcategory[]>> | null = null;
  readonly DataStateSubcategoryEnum = DataStateSubcategoryEnum;
subcategory:any;
  constructor(private subcategoryService: SubcategoryService, private router:Router, private route: ActivatedRoute) {}



  ngOnInit(): void {}

  onGetAllSubcategory() {
    this.subcategory$ = this.subcategoryService.getAllSubcategory().pipe(
      map(data => ({ dataState: DataStateSubcategoryEnum.LOADED, data: data.data })),
      startWith({ dataState: DataStateSubcategoryEnum.LOADING }),
      catchError(err => of({ dataState: DataStateSubcategoryEnum.ERROR, errorMessage: err.message }))
    );
  }

  onSearchSubcategory(dataForm: any) {
    this.subcategory$ = this.subcategoryService.SearchSubcategory(dataForm.keyword).pipe(
      map(data => ({ dataState: DataStateSubcategoryEnum.LOADED, data: data.data })),
      startWith({ dataState: DataStateSubcategoryEnum.LOADING }),
      catchError(err => of({ dataState: DataStateSubcategoryEnum.ERROR, errorMessage: err.message }))
    );
  }

  onDeleteSubcategory(sub: Subcategory) {
    let v = confirm("Are you sure you want to delete this?");
    if (v === true) {
      this.subcategoryService.deleteSubcategory(sub).subscribe(() => {
        this.onGetAllSubcategory();
      });
    }
  }
  onNewSubcategory(){
    this.router.navigateByUrl("/newSubcategory");
  }


editSubcategory(cat: any) {
  console.log(cat);
  this.router.navigateByUrl("/subcategory-update/" + cat._id);
}


onActionSubcategoryEvent($event: ActionSubcategoryEvent) {
 switch($event.type){
  case SubcategoryActionTypes.GET_ALL_SUBCATEGORY: this.onGetAllSubcategory(); break;
  case SubcategoryActionTypes.SEARCH_SUBCATEGORY: this.onSearchSubcategory($event.payload); break;
  case SubcategoryActionTypes.EDIT_SUBCATEGORY: this.editSubcategory($event.payload); break;
  case SubcategoryActionTypes.NEW_SUBCATEGORY: this.onNewSubcategory(); break;
  case SubcategoryActionTypes.DELETE_SUBCATEGORY: this.onDeleteSubcategory($event.payload); break;
 }
  }
}

