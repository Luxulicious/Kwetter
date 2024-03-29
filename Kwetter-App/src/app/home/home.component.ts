import {Component, OnInit, ViewChild} from '@angular/core';
import {PostService} from '../services/post/post.service';
import {Post} from '../models/post';
import {CreatePostComponent} from './create-post/create-post.component';
import {TimelineComponent} from './timeline/timeline.component';
import {AuthenticationService} from '../services/authentication/authentication.service';
import {Router} from '@angular/router';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

    @ViewChild(CreatePostComponent) 
    private createPostChild: CreatePostComponent;
    @ViewChild(TimelineComponent) 
    private timelineChild: TimelineComponent;

    searchCriteria: string = null;
    searchedPosts: Post[];

    constructor(private router: Router, private postService: PostService, private authenticationService: AuthenticationService) {}

    ngOnInit() {
        if (!this.authenticationService.getSignedInUserId()) {
            this.authenticationService.redirectToSignIn();
        }
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

    goToProfile(userId: number) {
        this.router.navigate(["profile/" + userId]);
    }
        
    refreshTimeline(userId: number) {
        this.timelineChild.refreshTimeline();
    }
    
    addPostToTimeline(post: Post) {
        this.timelineChild.addPost(post);
    }


}
