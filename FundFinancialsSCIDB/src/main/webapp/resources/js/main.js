(function(){

	window.App = {
			Routers: {},
			Views: {},
			Collections: {},
			Models: {},
			Urls: {},
			Data: {},
			TempData: {},
			DetailData: {data: [{id:1}, {id: 2}]},
			EventHandler: {},
			currentRouteLink: null,
			initData: function(rest_url){
				$.ajax({
			         url: App.Urls.baseUrl+ '/api/' + rest_url,
			         success: function(data) {
			        	 	App.Data = data;
							App.TempData.balanceModelsArray = [];
							_.each(App.Data.balance.values, function(element){
								App.TempData.balanceModelsArray.push(new App.Models.Balance(element));
							});
							App.TempData.cashFlowModelsArray = [];
							_.each(App.Data.cashFlow.values, function(element){
								App.TempData.cashFlowModelsArray.push(new App.Models.CashFlow(element));
							});
							App.TempData.partnerCapitalModelsArray = [];
							_.each(App.Data.partnerCapital.values, function(element){
								App.TempData.partnerCapitalModelsArray.push(new App.Models.PartnerCapital(element));
							});
							App.TempData.statementsModelsArray = [];
							_.each(App.Data.statements.values, function(element){
								App.TempData.statementsModelsArray.push(new App.Models.Statements(element));
							});
							 
							
		             },
		             async:   false
				});
			},
			initialize: function(){
				
				
				
				$('#controlPanelDiv').html(new App.Views.ControlPanel().render().el);
				
				var router = new App.Routers.MainRouter();				
				Backbone.history.start();
				
				App.initData('financials/full');				
				$("#tabDiv").empty().append(new App.Views.MainView().render().el);
				new App.Views.TableView().render().swapView(null);
				router.navigate("show_balance", {trigger: true});
				
				App.EventHandler = _.extend({}, Backbone.Events);
				App.EventHandler.on('show_balance', function(item){
					router.navigate("show_balance", {trigger: true});
				});
				App.EventHandler.on('show_cash_flow', function(item){
					router.navigate("show_cash_flow", {trigger: true});
				});
				App.EventHandler.on('show_partner_capital', function(item){
					router.navigate("show_partner_capital", {trigger: true});
				});
				App.EventHandler.on('show_statements', function(item){
					router.navigate("show_statements", {trigger: true});
				});
				
				App.EventHandler.on('show_button', function(data){
					if (data.from == "") data.from = "blank";
					if (data.till == "") data.till = "blank";
					var str = 'financials/dates/' + data.from + '/' + data.till;					
					App.initData(str);				
					router.navigate("/", {trigger: false});
					router.navigate(App.currentRouteLink, {trigger: true});
				});
				
			}		
			
			
	};
	
	
	
	
	App.Models.Balance = Backbone.Model.extend({
		
	});
	
	App.Models.CashFlow = Backbone.Model.extend({
		
	});
	
	App.Models.PartnerCapital = Backbone.Model.extend({
		
	});
	
	App.Models.Statements = Backbone.Model.extend({
		
	});
	
	App.Views.ItemsDetailTableView = Backbone.View.extend({
		tagName: 'div',		   	
		render: function(){
			var innerHtml = '<table class="modal-table">';
			
			if (App.DetailData != null){
				for (var i = 0; i < App.DetailData.items.length; i++){
					innerHtml += '<tr><td>' + JSON.stringify(App.DetailData.items[i]) + '</td></tr>'; 
				}
			}
			innerHtml += '</table>';
			this.$el.html(innerHtml);			
			
			return this;
		}
		
	});
		
	
	App.Views.ItemsDetailView = Backbone.ModalView.extend(
			{
			    name: "Modal Window",
			    model: App.Models.Statements,
			    templateHtml:
			        "<h1>hi</h1>",
			    initialize:
			        function()
			        {
			            _.bindAll( this, "render");
			            
			            this.initData(1);
			            
			            //this.template = _.template( this.templateHtml);
			            
			            
			        },
			    events: {
			    		"click .show" : "show",	
					},	
				"show": function(){
					this.initData(1);
					this.swapViews();					
				},	
			    render: function(){
			    	
			    	
			    	if (this.currentTableView != null) this.currentTableView.remove();		            
			    	this.currentTableView = new App.Views.ItemsDetailTableView();
			    	
			    	this.template = "<h1>";
		            this.template += this.model.get('chart');
		            this.template += "</h1>";
		            this.template += '<div id="modalTableContainer"> </div>';	            
		            
		            this.template += '<INPUT TYPE="button" value="Show" class="show" id="modal_button"/>';
			        $(this.el).html( this.template);
			        this.currentTableView.setElement(this.$('#modalTableContainer')).render();
			        return this;
			    },
			    currentTableView: null,  
			    swapViews: function(){
			    	if (this.currentTableView != null) this.currentTableView.remove();		            
			    	this.currentTableView = new App.Views.ItemsDetailTableView();
			    	
			    	this.currentTableView.setElement(this.$('#modalTableContainer')).render();
		            
			    },			    
			    getInfo: function(){
			    	var from_str = $('#datepicker_from').val();
					var till_str = $('#datepicker_till').val();
			    	if (from_str == "") from_str = "blank";
					if (till_str == "") till_str = "blank";
					
			    	if (App.currentRouteLink == "show_balance"){
			    		return {
			    			tabIndex: 1,
			    			from: from_str,
			    			till: till_str
			    		};
			    	} else if (App.currentRouteLink == "show_statements"){
			    		return {
			    			tabIndex: 2,
			    			from: from_str,
			    			till: till_str
			    		};
			    	} else if (App.currentRouteLink == "show_cash_flow"){
			    		return {
			    			tabIndex: 3,
			    			from: from_str,
			    			till: till_str
			    		};
			    	} else {
			    		return {
			    			tabIndex: 4,
			    			from: from_str,
			    			till: till_str
			    		};
			    	}
			    },    
			    initData: function(page){
			    	var infoObj = this.getInfo();
			    	var rest_url = 'financials/details/' + infoObj.tabIndex + '/' + this.model.get('chart') + '/' + infoObj.from + '/' + infoObj.till + '/' + page;
			    	$.ajax({
				         url: App.Urls.baseUrl+ '/api/' + rest_url,
				         success: function(data) {				        	 	
				        	alert(this.url);	
							alert(JSON.stringify(data));
							App.DetailData = data;
								
			             },
			             async:   false
					});
			    }    
			});
	
	App.Views.ControlPanel = Backbone.View.extend({
		tagName: 'div',
		initialize: function(){
        	
    	},
    	events: {
    		"click .show" : "show",	
		},
		render: function(){
			var template = _.template($('#control_panel').html());
			this.$el.html(template);			
			
			return this;
		},
		"show" : function(event){
			var from_str = $('#datepicker_from').val();
			var till_str = $('#datepicker_till').val();
			App.EventHandler.trigger('show_button', {from:from_str, till: till_str});
		}
	});
	
	App.Views.TabPanel = Backbone.View.extend({
		tagName: 'div',
		initialize: function(){
        	
    	},
    	events: {
			"click .balance" : "showBalance",
			"click .cash_flow" : "showCashFlow",
			"click .partner_capital" : "showPartnerCapital",
			"click .statements" : "showStatements",
		},    	
		render: function(){
			var template = _.template($('#tab_panel').html());
			this.$el.html(template);			
			
			return this;
		},
		
		"showBalance": function(event){
			App.EventHandler.trigger('show_balance');			
		},
		"showCashFlow": function(event){
			App.EventHandler.trigger('show_cash_flow');
		},
		"showPartnerCapital": function(event){
			App.EventHandler.trigger('show_partner_capital');
		},
		"showStatements": function(event){
			App.EventHandler.trigger('show_statements');
		},
	});
	
	App.Views.BalanceItem = Backbone.View.extend({
		tagName: 'tr',
		initialize: function(){
        	
    	},    		
		render: function(){
			
			if (this.model.get('showValue') != true) this.$el.addClass('miss_value');
			if (this.model.get('bold') == true) this.$el.addClass('bold_row');
			if (this.model.get('chart') == "") this.$el.addClass('empty_row');
			
			var template = "<td>" + this.model.get('chart')+ "</td>";
			if (this.model.get('showValue') != true){
				template += ("<td></td>");
			} else {
				template += ("<td ><span data-id=\""+ this.model.cid +"\" class=\"detail_link\">" + $.formatNumber(this.model.get('value'), {format:"#,###", locale:"us"}) + "</span></td>");
				
			}
			this.$el.html(template);			
			
			return this;
		}
	});
	
	App.Views.CashFlowItem = Backbone.View.extend({
		tagName: 'tr',
		initialize: function(){
        	
    	},		
		render: function(){
			
			if (this.model.get('showValue') != true) this.$el.addClass('miss_value');
			if (this.model.get('bold') == true) this.$el.addClass('bold_row');
			if (this.model.get('chart') == "") this.$el.addClass('empty_row');
			
			var template = "<td>" + this.model.get('chart')+ "</td>";
			if (this.model.get('showValue') != true){
				template += ("<td></td>");
			} else {
				template += ("<td ><span data-id=\""+ this.model.cid +"\" class=\"detail_link\">" + $.formatNumber(this.model.get('value'), {format:"#,###", locale:"us"}) + "</span></td>");
			}
			this.$el.html(template);			
			
			return this;
		}
	});
	
	App.Views.PartnerCapitalItem = Backbone.View.extend({
		tagName: 'tr',
		initialize: function(){
        	
    	},		
		render: function(){
			
			
			if (this.model.get('chart') == ""){
				this.$el.addClass('empty_row');
				template += ("<td></td><td></td><td></td><td></td>");
			} else {
				var template = "<td>" + this.model.get('chart')+ "</td>";
				template += ("<td ><span data-id=\""+ this.model.cid +"\" class=\"detail_link\">" + $.formatNumber(this.model.get('valueLP'), {format:"#,###", locale:"us"}) + "</span></td>");
				template += ("<td ><span data-id=\""+ this.model.cid +"\" class=\"detail_link\">" + $.formatNumber(this.model.get('valueGP'), {format:"#,###", locale:"us"}) + "</span></td>");
				template += ("<td ><span data-id=\""+ this.model.cid +"\" class=\"detail_link\">" + $.formatNumber(this.model.get('valueTotal'), {format:"#,###", locale:"us"}) + "</span></td>");				
			}
			
			this.$el.html(template);			
			
			return this;
		}
	});
	
	App.Views.StatementsItem = Backbone.View.extend({
		tagName: 'tr',
		initialize: function(){
        	
    	},		
		render: function(){
			
			if (this.model.get('showValue') != true) this.$el.addClass('miss_value');
			if (this.model.get('bold') == true) this.$el.addClass('bold_row');
			if (this.model.get('chart') == "") this.$el.addClass('empty_row');
			
			var template = "<td>" + this.model.get('chart')+ "</td>";
			if (this.model.get('showValue') != true){
				template += ("<td></td>");
			} else {
				//template += ("<td>" + this.model.get('value')+ "</td>");
				template += ("<td ><span data-id=\""+ this.model.cid +"\" class=\"detail_link\">" + $.formatNumber(this.model.get('value'), {format:"#,###", locale:"us"}) + "</span></td>");
			}
			this.$el.html(template);			
			
			return this;
		}
	});
	
	App.Collections.BalanceCollection = Backbone.Collection.extend({
		model: App.Models.Balance
	});
	
	App.Collections.CashFlowCollection = Backbone.Collection.extend({
		model: App.Models.CashFlow
	});
	
	App.Collections.PartnerCapitalCollection = Backbone.Collection.extend({
		model: App.Models.PartnerCapital
	});
	
	App.Collections.StatementsCollection = Backbone.Collection.extend({
		model: App.Models.Statements
	});
	
	
	App.Views.BalanceCollectionView = Backbone.View.extend({
		tagName: 'div',
		initialize: function(){
        	  
    	},
    	events: {
    	    "click .detail_link":  "openDetail"    	    
    	},	
		render: function(){
			this.$el.html('');
			(this.collection).each(function (item) {	
				this.$el.append(new App.Views.BalanceItem({model: item}).render().el);
			}, this);
			var innerHtml = '<table class="data_table"><thead><tr><th>Chart of Account</th><th>Amount</th></tr></thead>';			
			innerHtml += this.$el.html();
			innerHtml += '</table>';
			this.$el.html(innerHtml);
			this.$el.addClass('collection_element');
			return this;
		},
		"openDetail": function(event){
			event.preventDefault();
	        var id = $(event.currentTarget).data("id");	        
	        var item = this.collection.getByCid(id);
			showModalView(item);
		} 
	});	
	
	App.Views.CashFlowCollectionView = Backbone.View.extend({
		tagName: 'div',
		initialize: function(){
        	  
    	},
    	events: {
    	    "click .detail_link":  "openDetail"    	    
    	},
		render: function(){
			this.$el.html('');						
			(this.collection).each(function (item) {				
				this.$el.append(new App.Views.CashFlowItem({model: item}).render().el);
			}, this);			
			var innerHtml = '<table class="data_table"><thead><tr><th>Chart of Account</th><th>Amount</th></tr></thead>';			
			innerHtml += this.$el.html();
			innerHtml += '</table>';
			this.$el.html(innerHtml);
			this.$el.addClass('collection_element');
			return this;
		},
		"openDetail": function(event){
			event.preventDefault();
	        var id = $(event.currentTarget).data("id");	        
	        var item = this.collection.getByCid(id);
			showModalView(item);
		} 
	});
	
	App.Views.PartnerCapitalCollectionView = Backbone.View.extend({
		tagName: 'div',
		initialize: function(){
        	  
    	},
    	events: {
    	    "click .detail_link":  "openDetail"    	    
    	},
		render: function(){
			this.$el.html('');				
			(this.collection).each(function (item) {				
				this.$el.append(new App.Views.PartnerCapitalItem({model: item}).render().el);
			}, this);			
			var innerHtml = '<table class="data_table"><thead><tr><th>Chart of Account</th><th>Amount LP</th><th>Amount GP</th><th>Total Amount</th></tr></thead>';			
			innerHtml += this.$el.html();
			innerHtml += '</table>';
			this.$el.html(innerHtml);
			this.$el.addClass('collection_element');
			return this;
		},
		"openDetail": function(event){
			event.preventDefault();
	        var id = $(event.currentTarget).data("id");	        
	        var item = this.collection.getByCid(id);
			showModalView(item);
		} 
	});
	
	App.Views.StatementsCollectionView = Backbone.View.extend({
		tagName: 'div',
		initialize: function(){
        	  
    	},
    	events: {
    	    "click .detail_link":  "openDetail"    	    
    	},
		render: function(){			
			this.$el.html('');						
			(this.collection).each(function (item) {				
				this.$el.append(new App.Views.StatementsItem({model: item}).render().el);
			}, this);			
			var innerHtml = '<table class="data_table"><thead><tr><th>Chart of Account</th><th>Amount</th></tr></thead>';			
			innerHtml += this.$el.html();
			innerHtml += '</table>';
			this.$el.html(innerHtml);
			this.$el.addClass('collection_element');
			return this;
		},
		"openDetail": function(event){
			event.preventDefault();
	        var id = $(event.currentTarget).data("id");	        
	        var item = this.collection.getByCid(id);
			showModalView(item);
		} 
	});	
	
	
	App.Views.MainView = Backbone.View.extend({
		tagName: 'div',
		initialize: function(){			
		},
		render: function(){
			this.$el.html('');								
			this.$el.append(new App.Views.TabPanel().render().el);			
			return this;
		}
	});
	
	App.Views.TableView = Backbone.View.extend({
		el: '#tableDiv',
		initialize: function(){
			
		},
		render: function(){		
			
			this.$el.html('');
					
			
			return this;
		},
		swapView: function(v){			
			if (this.view != null) {				
				this.view.remove();
			}
			this.view = v;
			if (this.view != null){
				$('#tableDiv').html(this.view.render().el);
			} else {
				$('#tableDiv').empty();
			}
			return this;
		},
		rerender: function(){
			if (this.view != null){ 
				$('#tableDiv').empty().html(this.view.render().el);
			}
			return this;
		},
		view: null
	});

	
	

	
	App.Routers.MainRouter = Backbone.Router.extend({
		routes: {
			"main" : "main",
			"show_balance"         : "show_balance",
			"show_cash_flow"       : "show_cash_flow",
			"show_partner_capital" : "show_partner_capital",
			"show_statements"      : "show_statements"
		},
		main: function(){
			App.currentRouteLink = "main";
			$("#tabDiv").empty().append(new App.Views.MainView().render().el);
			new App.Views.TableView().render().swapView(null);
			
		},
		show_balance: function(){
			App.currentRouteLink = "show_balance";
			$('.tabs .tab-links .balance span').parent('li').addClass('active').siblings().removeClass('active');	 
			var col = new App.Collections.BalanceCollection(App.TempData.balanceModelsArray);
			var v1 = new App.Views.BalanceCollectionView({collection: col});			
			new App.Views.TableView().render().swapView(v1);
		},
		show_cash_flow: function(){		
			App.currentRouteLink = "show_cash_flow";
			$('.tabs .tab-links .cash_flow span').parent('li').addClass('active').siblings().removeClass('active');
			var col = new App.Collections.CashFlowCollection(App.TempData.cashFlowModelsArray);
			var v1 = new App.Views.CashFlowCollectionView({collection: col});
			new App.Views.TableView().render().swapView(v1);
		},
		show_partner_capital: function(){
			App.currentRouteLink = "show_partner_capital";
			$('.tabs .tab-links .partner_capital span').parent('li').addClass('active').siblings().removeClass('active');
			var col = new App.Collections.PartnerCapitalCollection(App.TempData.partnerCapitalModelsArray);
			var v1 = new App.Views.PartnerCapitalCollectionView({collection: col});
			new App.Views.TableView().render().swapView(v1);
		},
		show_statements: function(){
			App.currentRouteLink = "show_statements";
			$('.tabs .tab-links .statements span').parent('li').addClass('active').siblings().removeClass('active');
			var col = new App.Collections.StatementsCollection(App.TempData.statementsModelsArray);
			var v1 = new App.Views.StatementsCollectionView({collection: col});
			new App.Views.TableView().render().swapView(v1);	
			
			
			
			
		}
	});
	
	function showModalView(modelIn){
		
		new App.Views.ItemsDetailView({model: modelIn}).render().showModal({
		    x: 200,
		    y: 200,
		    fadeInDuration:150,
		    fadeOutDuration:150,
		    showCloseButton:true,
		    bodyOverflowHidden:false,
		    setFocusOnFirstFormControl:true,
		    targetContainer: document.body,
		    slideFromAbove: false,
		    slideFromBelow: false,
		    slideDistance: 150,
		    closeImageUrl: "resources/close-modal.png",
		    closeImageHoverUrl: "resources/close-modal-hover.png",
		    css:
		    {	
		    	"width": "800px",		    	
		        "border": "2px solid #111",
		        "background-color": "#fff",
		        "-webkit-box-shadow": "0px 0px 15px 4px rgba(0, 0, 0, 0.5)",
		        "-moz-box-shadow": "0px 0px 15px 4px rgba(0, 0, 0, 0.5)",
		        "box-shadow": "0px 0px 15px 4px rgba(0, 0, 0, 0.5)",
		        "-webkit-border-radius": "10px",
		        "-moz-border-radius": "10px",
		        "border-radius": "10px"
		    }});
	}
	
	
	
	
})();

