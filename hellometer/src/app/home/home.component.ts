import { Component, OnInit } from '@angular/core';
import {Chart} from '../model/chart';
import {ChartService} from '../services/chart.service';
import * as fs from 'file-saver';

@Component({
  selector: 'app-all-data-chart',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  chart: Chart;
  chartData: any;
  showChart: boolean;

  startDate: string;
  endDate: string;

  constructor(private chartService: ChartService) {
  }

  showFileUpload = false;
  fileToUpload: File = null;
  showHelper = false;

  handleFileInput(files: FileList): void {
    this.fileToUpload = files.item(0);
    this.uploadFile();
  }

  uploadFile(): void {
    this.chartService.uploadPricesFile(this.fileToUpload).then(result => {
      if (result.success) {
        alert('File uploaded');
        this.showFileUpload = false;
        this.getCustomersPerHour();
      }
    });
  }

  downloadFile(): void {
    this.chartService.downloadFile().then(result => {
      fs.saveAs(result, 'data.csv');
    });
  }

  makeBarChart(): void {
    this.chart.chartType = 'bar';
  }

  makeLineChart(): void {
    this.chart.chartType = 'line';
  }

  helper(): void {
    this.showHelper = true;
    this.showChart = false;
  }

  clearData(): void {
    this.ngOnInit();
    this.chartService.clearData().then(result => {
      if (result.success) {
        alert('Data cleared');
        this.helper();
      }
    });
  }


  getCustomers(): void {
    this.ngOnInit();
    this.showHelper = false;
    this.chartService.getCustomers().then(response => {
      this.chartData = response;
      console.log(this.chartData);
      const barCharDataElement = {
        data: [],
        label: null
      };
      for (const data of this.chartData) {
        barCharDataElement.data.push(data.tts);
        this.chart.chartLabels.push(data.first_seen);
      }
      barCharDataElement.label = 'All Data';
      this.chart.chartData.push(barCharDataElement);
      this.showChart = true;
    }
  );
  }

  getCustomersPerHour(): void {
    this.ngOnInit();
    this.showHelper = false;
    this.chartService.getCustomersPerHour().then(response => {
        this.chartData = response;
        const barCharDataElement = {
          data: [],
          label: null
        };
        for (const data of this.chartData) {
          barCharDataElement.data.push(data.customers);
          this.chart.chartLabels.push(data.hour);
        }
        barCharDataElement.label = 'Average Number of Customers Per Hour';
        this.chart.chartData.push(barCharDataElement);
        this.showChart = true;
      }
    );
  }

  getCustomersPerDayPart(): void {
    this.ngOnInit();
    this.showHelper = false;
    this.chartService.getCustomersPerDayPart().then(response => {
        this.chartData = response;
        const barCharDataElement = {
          data: [],
          label: null
        };
        for (const data of this.chartData) {
          barCharDataElement.data.push(data.customers);
          this.chart.chartLabels.push(data.dayPart);
        }
        barCharDataElement.label = 'Average Number of Customers Per Day Part';
        this.chart.chartData.push(barCharDataElement);
        this.showChart = true;
      }
    );
  }

  getServicePerHour(): void {
    this.ngOnInit();
    this.showHelper = false;
    this.chartService.getServicePerHour().then(response => {
        this.chartData = response;
        const barCharDataElement = {
          data: [],
          label: null
        };
        for (const data of this.chartData) {
          barCharDataElement.data.push(data.averageTts);
          this.chart.chartLabels.push(data.hour);
        }
        barCharDataElement.label = 'Average Service Time Per Hour';
        this.chart.chartData.push(barCharDataElement);
        this.showChart = true;
      }
    );
  }

  getServicePerDayPart(): void {
    this.ngOnInit();
    this.showHelper = false;
    this.chartService.getServicePerDayPart().then(response => {
        this.chartData = response;
        const barCharDataElement = {
          data: [],
          label: null
        };
        for (const data of this.chartData) {
          barCharDataElement.data.push(data.averageTts);
          this.chart.chartLabels.push(data.dayPart);
        }
        barCharDataElement.label = 'Average Service Time Per Day Part';
        this.chart.chartData.push(barCharDataElement);
        this.showChart = true;
      }
    );
  }

  ngOnInit(): void {
    this.chartData = [];
    this.chart = new Chart();
    this.chart.chartData = [];
    this.chart.chartLegend = true;
    this.chart.chartLabels = [];
    this.chart.chartType = 'bar';
    this.showChart = false;
    this.showHelper = true;
  }

}
