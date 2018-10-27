import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {BaseAngularComponent} from '../shared/components/base-angular/base-angular.component';
import {MatSort, MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent extends BaseAngularComponent implements OnInit, AfterViewInit {
  displayedColumns = ['name', 'createdDate', 'comment', 'status', 'total'];
  dataSource = new MatTableDataSource([]);
  @ViewChild(MatSort) sort: MatSort;

  constructor(protected router: Router, protected route: ActivatedRoute) {
    super();
  }

  ngOnInit() {
    super.ngOnInit();

    // if (this.reports == null && this.route.snapshot.data['data']) {
    //   this.reports = this.route.snapshot.data['data'];
    // }
  }

  ngAfterViewInit() {
    this.initializeForDisplay();
  }

  initializeForDisplay() {
    // this.reports.forEach((report: Report) => {
    //   report['total'] = report.getTotalExpenses() + report.getTotalTimes();
    // });

    this.dataSource = new MatTableDataSource([]);
    this.dataSource.sort = this.sort;
  }

  createBtnClicked() {
    // this.router.navigate(['/reports/create']);
  }
}
