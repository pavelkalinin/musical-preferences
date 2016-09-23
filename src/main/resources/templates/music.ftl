<!DOCTYPE HTML>
<html>
	<head>
		<title>Music &#64 Stable</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="enhorse's personal site" />
		<meta name="keywords" content="music, hobby, indie, electronic, rock, pop, iTunes, Google Music, Yandex Music, AllMusic.com" />

        <link rel="icon" href="/favicon.ico">

        <!--[if lte IE 8]>
		<script src="/css/ie/html5shiv.js"></script>
        <![endif]-->
        <script src="/js/jquery.min.js"></script>
        <script src="/js/skel.min.js"></script>
        <script src="/js/init.js"></script>
        <noscript>
            <link rel="stylesheet" href="/css/skel.css" />
            <link rel="stylesheet" href="/css/style.css" />
            <link rel="stylesheet" href="/css/style-desktop.css" />
            <link rel="stylesheet" href="/css/style-noscript.css" />
        </noscript>
        <!--[if lte IE 8]>
		<link rel="stylesheet" href="/css/ie/v8.css" />
        <![endif]-->
	</head>
	<body>
		<script>
   			(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
   			(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
   			m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
   			})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		 	ga('create', 'UA-40043991-2', 'auto');
   			ga('send', 'pageview');
		</script>
		
		<!-- Wrapper-->
			<div id="wrapper">
				
					<!-- Navigation -->
					<nav id="nav">
						<a href="/index.html#main" class="icon fa-home"><span>Home</span></a>
						<a href="/index.html#artifacts" class="icon fa-folder active"><span>Artifacts</span></a>
						<a href="#comment" class="icon fa-comment"><span>Comments</span></a>
					</nav>

					<!-- Content -->
					<div id="main">				

							<article id="work" class="panel">
								<header>
									<h1 style="color:red">Music / Музыка</h1>
									<p>pleasantly to the ear / приятно на слух</p>
								</header>
								
								<section>
									<div class="row">
										<#list albums as webAlbum>
											<div class="3u tooltip music">
												<a href="${webAlbum.source}" target="_blank"	class="image fit cover">
													<span>${webAlbum.artist} • ${webAlbum.name}</span>
													<img src="${webAlbum.cover}" alt="${webAlbum.description}" />
												</a>
											</div>
										</#list>
									</div>						
								</section>
							</article>	

							<!-- Comments Part -->
							<article id="comment" class="panel">
								<header>
									<h2>Suggestions / Предложения</h2>
								</header>
								<div id="disqus_thread"></div>    
								<script type="text/javascript">
									var disqus_shortname = 'enhorseus';
									(function() {
										var dsq = document.createElement('script'); 
										dsq.type = 'text/javascript'; 
										dsq.async = true;
										dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
										(document.getElementsByTagName('head')[0] 
											|| document.getElementsByTagName('body')[0]).appendChild(dsq);
									})();
								</script>
								<noscript>Please enable JavaScript to view the <a href="https://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
							</article>
					</div>
		
					<!-- Footer -->
					<div id="footer">
						<ul class="copyright">
							<li>06/2014 - ...</li>
						</ul>
					</div>
		
			</div>

	</body>
</html>