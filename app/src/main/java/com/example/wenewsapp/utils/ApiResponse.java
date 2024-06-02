package com.example.wenewsapp.utils;

import com.example.wenewsapp.News;

import java.util.List;

public class ApiResponse {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public static class Response {
        private List<News> results;

        public List<News> getResults() {
            return results;
        }
    }
}
