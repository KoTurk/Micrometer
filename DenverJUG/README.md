## From my talk  "Controlling your race with Micrometer and Spring Boot"
[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/kqNMOJRfGIg/0.jpg)](https://youtu.be/kqNMOJRfGIg)

### Slides <br>
[![IMAGE ALT TEXT HERE](https://image.slidesharecdn.com/djugmicromer-210211015438/95/controlling-your-race-with-micrometer-and-spring-boot-live-coding-18-638.jpg?cb=1613008532)](https://www.slideshare.net/KoTurk/controlling-your-race-with-micrometer-and-spring-boot-live-coding)

### Micrometer
In this repository you will find the micrometer demo I live coded at the Denver JUG.<br>
Please build:
<pre><code>mvn clean install</code></pre>

And run Spring Boot with:
<pre><code>mvn spring-boot:run</code></pre>


### Prometheus
Please note that I downloaded and run Prometheus.
You can download it from:<br>
https://prometheus.io/download/ <br><br>

Don't forget to change the target url to your micrometer instance (targets) 
<pre><code>   static_configs:
    - targets: ['localhost:8080']
</code></pre>

### Grafana
Please note that I downloaded and run Grafana as my preferred monitoring dashboard tool. <br> 
You can download it from here: https://grafana.com/grafana/download <br><br>
