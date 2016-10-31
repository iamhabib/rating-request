# Rating Request [ ![Download](https://api.bintray.com/packages/osongae2/maven/rating-request/images/download.svg) ](https://bintray.com/osongae2/maven/rating-request/_latestVersion)
-------------------

RatingRequest library is a simple android dialog for request rating and review.

##Usage
-----------

Add below line in app ```build.gradle```
 
```gradle
dependencies {
	compile 'com.iamhabib:rating-request:1.0.0'
}
```

##Code snippets
---------------

Easy way to show the dialog:

```groovy
RatingRequest.with(this)
                .scheduleAfter(7) // invoke when later button click, default 5
                .agreeButtonText("Sure!")
                .laterButtonSeletor(R.drawable.button_accept)
                .laterButtonText("Later")
                .doneButtonText("Already Done")
                .backgroundResource(R.color.colorPrimary)
                .message("Are you enjoying our app?\n Please give us a review")
                .listener(new RatingRequest.ClickListener() {
                    @Override
                    public void onAgreeButtonClick() {

                    }

                    @Override
                    public void onDoneButtonClick() {
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onLaterButtonClick() {

                    }
                })
                .cancelable(false) // default true
                .delay(10 * 1000) // after 10 second dialog will be shown, default 1000 milliseconds
                .register();
```

##License

Copyright 2016 Habibur Rahman

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.