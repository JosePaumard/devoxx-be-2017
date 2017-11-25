# Devoxx BE 2017 Live Coding

This repo is an extended and somehow cleaned up version of the live coding sessions I made for my talk _Collectors in the Wild_ at Devoxx Belgium 2017. 

You can get the slides here: https://www.slideshare.net/jpaumard/collectors-in-the-wild

And watch the video here: https://www.youtube.com/watch?v=yddwA3458eo&t=3874s

The Collector API has been added to the JDK 8, and was left in the shadow of the Stream API. It is quite logical, since a collector is made to consume the elements of a stream: no Stream, no Collector. We have a set of ready to use, very powerful collectors in the Collectors class: toList and groupingBy, to name a few. Those two facts hide the collector model to process data, and how powerful and efficient is this API.

This in depth talk is all about collectors: those available, because we need to know them, those that we can create, those we had no idea they could be created, and the others, as there is in fact no limit to what can be done with this API. The concept of downstream collector will be used to show how we can write entire data processing pipelines using collectors only, and pass them as parameters to other pipelines.

This repo is organized in 9 live coding sessions. You can check the code from the last commit, this will give you an overview of the finished state of all the sessions. You can also check the evolution of each session commit by commit. It will give you a step by step progress through the coding itself. I also created 9 tags in the repo for you to be able to jump directly to a given session. 

Enjoy!

### Setting up the Project

The code of this session uses a data file that you can download here: http://introcs.cs.princeton.edu/java/data/papers.lst and place in the `files` directory of this project. 

### Session 1

This first session shows the first basic Collectors patterns: count, max, statistics.  

### Session 2

We then show the grouping by pattern, especially useful to compute histograms. We compute the histogram of the number of articles published per year. Then we show how we can get the year that saw the most articles published. 

### Session 3

This third session shows how to count the number of times a given element appears in a stream. It is applied here to get the histogram of the number of articles published per author. Once we have this histogram, we apply the previous pattern to get the author that published the most. 

### Session 4

In this session, we show how we can use the collecting and then pattern to make the histogram and the key / value pair extraction in a single collector. Several patterns are rewritten using this collecting and then pattern. 


### Session 5

In fact, the max extraction only gives us the first max, and does not tell us if there are other. We show here how we can inverse the map to get the max value, along with all the keys associated to it. 

### Session 6

We then push the pattern one step further. Since we modeled the extraction of the most seen author in a year in a single collector, we can use this collector as a downstream collector to get the histogram of the most seen author per year. 

It turns out that when we do that, some values may be empty, and the `Optional.get()` that we are calling at the end of this downstream collector may be called on an empty optional, thus leading to an exception. 

So we show a pattern, based on flatmap, to solve this problem. Basically, this patterns consists in converting a `Map<K, Stream<V>>` with empty streams to a `Map<K, V>` where the keys associated to the empty streams have been removed using the flatmap.  

### Session 7

We go one step further with this pattern, and show how streams and collectors can be used to compute cross products. Here we extract the duo of authors that published the most together. We put this processing in a collector, then use is as a downstream collector to get the histogram of the most seen duos per year. We then apply the same map processing patterns to get rid of the keys associated with empty streams. 

### Session 8

This part was not presented in the talk. As a side note, we show how the previous pattern can be modified to convert a `Map<K, List<V>>` to a `Map<V, List<K>>`.   

### Session 9

This last live coding session, that was very messy during the talk (sorry about that, I was a little short on time), aims to improve the readability of the advanced collectors patterns we showed. In a nutshell, combining collectors consists in nesting collectors calls, which is very bad for readability. 

The following patterns are shown as examples of what could be done to improve the readability of complex collectors. They should not be used in favor of the corresponding streams patterns, since there are not efficient.  

We show a first possible improvement: how to chain the calls to the collect method. Is solves the readability problem (nested calls become chained calls on the stream object), but does not allow the modeling of a complex processing in a single collector.  

We then show a second possible improvement: how to compose collectors themselves to create complex collectors.

Both solutions work, but are not very efficient: a collector remains a terminal operation, and using one forces the computation of the whole stream at each step, which is not the case when using the corresponding stream pattern. 

So there is more work to be done here. 