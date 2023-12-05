import { Component, OnInit, ElementRef, Renderer2, AfterViewInit, ViewChild} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SubcategoryService } from 'src/app/services/subcategory.service';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';


@Component({
  selector: 'app-subcategory-add',
  templateUrl: './subcategory-add.component.html',
  styleUrls: ['./subcategory-add.component.css']
})
export class SubcategoryAddComponent implements OnInit {
  subcategoryFormGroup?:FormGroup;
  submitted:boolean =false;

  cats : any[] = [];





  constructor(private fb:FormBuilder,private subcategoryService:SubcategoryService) {}
  @ViewChild('myElementId', { static: false }) myElement!: ElementRef;











  ngOnInit(): void {
   this.subcategoryFormGroup = this.fb.group({
_id: [0,Validators.required],
subCategoryName: ["",Validators.required],
categoryId: [0, Validators.required],
 active: [true,Validators.required],

  });

  this.LoadCategory();
}
OneSaveSubcategory(){
  this.submitted=true;
  this.subcategoryService.saveSubcategory(this.subcategoryFormGroup?.value).subscribe(data => {
  alert("Subcategory saved");
})
}

LoadCategory() {
  this.subcategoryService.getAllCategory().subscribe(data  => {
    this.cats = data.data;
    console.log(this.cats);
  },error => {console.log('err');
  });
}


}



