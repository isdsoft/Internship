package com.example.easycoordinate.service;

import com.example.easycoordinate.model.Coordinate;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CoordinateService {

    private static Logger log = Logger.getLogger(CoordinateService.class.getName());

    public static List<Coordinate> getAvailableRoutes(List<Coordinate> allRoutes) {
        List<Coordinate> availableRoutes = new ArrayList<>();
        for (Coordinate coordinate : allRoutes) {
            if ( System.currentTimeMillis() < Long.parseLong(coordinate.getEndTime())) {
                availableRoutes.add(coordinate);
            }
        }
        return availableRoutes;
    }

    private static double getDistance(double lng1, double lat1, double lng2, double lat2){
        double R = 6371000; // Radius of the earth in m
        double dLat = deg2rad(lat2-lat1);  //transform in rad
        double dLon = deg2rad(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) *
                Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return (R * c);
    }

    private static double deg2rad(double deg){
        return (deg*(Math.PI/180));
    }

    public static List<Coordinate> getSameRoute(Coordinate clientPosition ,List<Coordinate>allRoute){
        double radius = 300f;
        List<Coordinate> result = new ArrayList<>();
        List<Coordinate> availableRoutes = getAvailableRoutes(allRoute);
        String[] startPointA = clientPosition.getCoordinateStart().split(":");
        String [] endPointA = clientPosition.getCoordinateEnd().split(":");

        for(Coordinate coord : availableRoutes){
            if ( clientPosition.getId().longValue() == coord.getId().longValue() )
                continue;
            String[] startPointB = coord.getCoordinateStart().split(":");
            String [] endPointB = coord.getCoordinateEnd().split(":");
            double distanceX = CoordinateService.getDistance(
                    Double.parseDouble(startPointA[0]),
                    Double.parseDouble(startPointA[1]),
                    Double.parseDouble(startPointB[0]),
                    Double.parseDouble(startPointB[1]));
            double distanceY = CoordinateService.getDistance(
                    Double.parseDouble(endPointA[0]),
                    Double.parseDouble(endPointA[1]),
                    Double.parseDouble(endPointB[0]),
                    Double.parseDouble(endPointB[1]));
            if ( distanceX < radius && distanceY < radius )
                result.add(coord);
        }

        return result;
    }

    public static List<Coordinate> getSameRouteWithoutStoringObject(String startCoordinate, String endCoordinate, List<Coordinate> allRoutes) {
        double radius = 1000f;
        List<Coordinate> result = new ArrayList<>();
        List<Coordinate> availableRoutes = getAvailableRoutes(allRoutes);
        String[] startPointA = startCoordinate.split(":");
        String[] endPointA = endCoordinate.split(":");

        for(Coordinate coord : availableRoutes){
            String[] startPointB = coord.getCoordinateStart().split(":");
            String[] endPointB = coord.getCoordinateEnd().split(":");
            double distanceX = CoordinateService.getDistance(
                    Double.parseDouble(startPointA[0]),
                    Double.parseDouble(startPointA[1]),
                    Double.parseDouble(startPointB[0]),
                    Double.parseDouble(startPointB[1]));
            double distanceY = CoordinateService.getDistance(
                    Double.parseDouble(endPointA[0]),
                    Double.parseDouble(endPointA[1]),
                    Double.parseDouble(endPointB[0]),
                    Double.parseDouble(endPointB[1]));
            if ( distanceX < radius && distanceY < radius )
                result.add(coord);
        }

        return result;
    }

}
