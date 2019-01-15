import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuartzJobsComponent } from './quartz-jobs.component';

describe('QuartzJobsComponent', () => {
  let component: QuartzJobsComponent;
  let fixture: ComponentFixture<QuartzJobsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuartzJobsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuartzJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
