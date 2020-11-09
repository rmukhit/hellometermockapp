import {Injectable} from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';


@Injectable()
export class ChartService {

  // BASE_URL: string = window.location.protocol + '//' + window.location.hostname + ':9000';
  BASE_URL: string = 'http://18.189.13.199:8080';


  constructor(private http: HttpClient) {}

  getCustomers(): Promise<any> {
    return new Promise(resolve => {
      this.http.get(this.BASE_URL + '/customers').subscribe((res: Response) => {
        // console.log(res);
        resolve(res);
      }, error => 'Error');
    });
  }


  getServicePerHour(): Promise<any> {
    return new Promise(resolve => {
      this.http.get(this.BASE_URL + '/averagettsperhour').subscribe((res: Response) => {
        // console.log(res);
        resolve(res);
      }, error => 'Error');
    });
  }

  getServicePerDayPart(): Promise<any> {
    return new Promise(resolve => {
      this.http.get(this.BASE_URL + '/averagettsperdaypart').subscribe((res: Response) => {
        // console.log(res);
        resolve(res);
      }, error => 'Error');
    });
  }

  getCustomersPerHour(): Promise<any> {
    return new Promise(resolve => {
      this.http.get(this.BASE_URL + '/averagecustomersperhour').subscribe((res: Response) => {
        // console.log(res);
        resolve(res);
      }, error => 'Error');
    });
  }

  getCustomersPerDayPart(): Promise<any> {
    return new Promise(resolve => {
      this.http.get(this.BASE_URL + '/averagecustomersperdaypart').subscribe((res: Response) => {
        // console.log(res);
        resolve(res);
      }, error => 'Error');
    });
  }



  uploadPricesFile(fileToUpload): Promise<any> {
    const formData: FormData = new FormData();
    formData.append('file', fileToUpload, fileToUpload.name);
    return new Promise(resolve => {
      this.http.post(this.BASE_URL + '/files/upload-data', formData).subscribe(
        response => resolve(response));
    });
  }

  downloadFile(): Promise<any> {
    return new Promise(resolve => {
      const formData: FormData = new FormData();
      this.http.post(this.BASE_URL + '/files/download-file', formData, {responseType: 'blob'}).subscribe(
        response => resolve(response),
        error => 'error'
      );
    });
  }

  clearData(): Promise<any> {
    return new Promise(resolve => {
      this.http.get(this.BASE_URL + '/cleardata').subscribe(
        response => resolve(response));
    });
  }

}
