import {Component, OnInit, ViewChild} from '@angular/core';
import {PostService} from '../services/post/post.service';
import {Post} from '../models/post';
import {CreatePostComponent} from './create-post/create-post.component';
import {TimelineComponent} from './timeline/timeline.component';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    //TODO Other children
    @ViewChild(CreatePostComponent) createPostChild: CreatePostComponent;
    @ViewChild(TimelineComponent) timelineChild: TimelineComponent;

    searchCriteria: string = null;
    searchedPosts: Post[];

    constructor(private postService: PostService) {}

    ngOnInit() {
    }

    ngAfterViewInit() {
        // child is set
    }

    searchPosts(): any {
        if (this.searchCriteria != null && this.searchCriteria != "") {
            this.postService.searchPosts(this.searchCriteria)
                .subscribe(response => {
                    console.log(response);
                    let posts: Post[] = response["Records"];
                    this.searchedPosts = posts;
                    this.setTimelineToMatchSearch(posts);
                },
                    error => {
                        //TODO Handle this error properly
                        console.log(error);
                    });
        }
        else {
            this.timelineChild.refreshTimeline();
        }
    }

    setTimelineToMatchSearch(posts: Post[]): void {
        this.timelineChild.setTimelineToMatchSearch(posts);
    }


}
