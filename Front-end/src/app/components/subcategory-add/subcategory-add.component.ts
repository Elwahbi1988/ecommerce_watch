import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SubcategoryService } from 'src/app/services/subcategory.service';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-subcategory-add',
  templateUrl: './subcategory-add.component.html',
  styleUrls: ['./subcategory-add.component.css']
})
export class SubcategoryAddComponent implements OnInit {
  subcategoryFormGroup?:FormGroup;
  submitted:boolean =false;

  cats : any[] = [];
  selectedOption: any;
  textFieldValue: any;

  constructor(private fb:FormBuilder,private subcategoryService:SubcategoryService) {}



  ngOnInit(): void {
   this.subcategoryFormGroup = this.fb.group({
id: [0,Validators.required],
subCategoryName: ["",Validators.required],
categoryId: [0, Validators.required],
 selected: [true,Validators.required],
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

updateTextField(): void {
  // Mettre à jour le champ texte en fonction de la sélection
  this.textFieldValue = 'Vous avez sélectionné : ' + this.selectedOption;
}
}



