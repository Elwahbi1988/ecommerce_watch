import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subcategory } from '../model/subcategory.model';

@Injectable({
  providedIn: 'root'
})
export class SubcategoryService {

  public host:string= "http://localhost:8085/";

  constructor(private http:HttpClient) { }

  getAllSubcategory(): Observable<any>{
    return this.http.get<any>(this.host + "v1/subcategories");
  }
  getSelectSubcategory(): Observable<Subcategory[]>{
    return this.http.get<Subcategory[]>(this.host + "v1/subcategories");
  }
  getAvailableSubcategory(): Observable<Subcategory[]>{
return this.http.get<Subcategory[]>(this.host + "v1/subcategories");
  }
  SearchSubcategory(keyword:string): Observable<any>{
    return this.http.get<any>(this.host + "v1/subcategories?query="+ keyword);
      }
selectSubcategory(subcategory:any): Observable<Subcategory>{
  subcategory.selected=!subcategory.selected
        return this.http.put<Subcategory>(this.host+"v1/subcategories/"+ subcategory._id,subcategory);
          }

deleteSubcategory(subcategory:any): Observable<void>{
                  return this.http.delete<void>(this.host+"v1/subcategories/"+ subcategory._id);
                    }

saveSubcategory(subcategory:any): Observable<any>{
 return this.http.post<any>(this.host+"v1/subcategories",subcategory);
                              }
getSubcategory(id:number): Observable<any>{
return this.http.get<Subcategory>(this.host + "v1/subcategories/" + id);
}
onUpdateSubcategory(subcategory: any): Observable<any> {
  return this.http.patch<any>(`${this.host}v1/subcategories/` + String(subcategory._id), subcategory);
}
getAllCategory(): Observable<any>{
  return this.http.get<any>(this.host + "v1/categories");
}

}
