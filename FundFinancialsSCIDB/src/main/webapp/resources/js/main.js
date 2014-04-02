(function(){

	window.App = {
			Routers: {},
			Views: {},
			Collections: {},
			initialize: function(){
				var router = new App.Routers.MainRouter();				
				Backbone.history.start();
				router.navigate("main", {trigger: true});
			}
	};
	
	
	
	App.Views.Header = Backbone.View.extend({
		tagName: 'div',
		initialize: function(){
        	
    	},
    	events: {
				
		},
		render: function(){
			var template = _.template($('#control_panel').html());
			this.$el.html(template);			
			
			return this;
		}
	});
	
	App.Views.MainView = Backbone.View.extend({
		tagName: 'div',
		initialize: function(){
			
		},
		render: function(){		
			
			this.$el.html('');
			this.$el.append('<H1>ToDo List</H1>');
			this.$el.append(new App.Views.Header().render().el);	
			return this;
		}
	});

	
	App.Routers.MainRouter = Backbone.Router.extend({
		routes: {
			"main" : "main",
			"items/:id" : "viewItem"
		},
		main: function(){
			$("#mainDiv").empty().append(new App.Views.MainView().render().el);
		}
	});
	
	
	
	
	
	
})();

