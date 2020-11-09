
export class Chart {

  public chartOptions;
  public chartLabels;
  public chartType;
  public chartLegend;
  public chartData;
  public chartColors

  constructor() {
    this.chartOptions = {
      scaleShowVerticalLines: false,
      responsive: true
    };

    this.chartColors = {
      backgroundColor: 'blue'
    };
  }

}
