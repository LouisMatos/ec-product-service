package br.matosit.product_service.adapters.in.rest.responses;

public record ProductResponse(
		  String id,
		  String name,
		  String description,
		  Double price,
		  Integer stockQuantity,
		  String image3D
		) {}