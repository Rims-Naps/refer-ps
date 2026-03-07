package com.ruse.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static String getTimeRemainingD(LocalDateTime now, LocalDateTime end) {
        Duration duration = Duration.between(now, end);
        long daysRemaining = duration.toDays();
        return "Time Left: " + daysRemaining + "D";
    }
    public static String getTimeRemainingDH(LocalDateTime now, LocalDateTime end) {
        Duration duration = Duration.between(now, end);
        long daysRemaining = duration.toDays();
        long hoursRemaining = duration.toHours() % 24;
        return "Time Left: " + daysRemaining + "D " + hoursRemaining + "H";
    }
    public static String getTimeRemainingDHM(LocalDateTime now, LocalDateTime end) {
        Duration duration = Duration.between(now, end);
        long daysRemaining = duration.toDays();
        long hoursRemaining = duration.toHours() % 24;
        long minutesRemaining = duration.toMinutes() % 60;
        return "Time Left: " + daysRemaining + "D " + hoursRemaining + "H " + minutesRemaining + "M";
    }

    public static class LocalDateTimeAdapter
        implements JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        @Override
        public com.google.gson.JsonElement serialize(LocalDateTime src, java.lang.reflect.Type typeOfSrc, com.google.gson.JsonSerializationContext context) {
            return new com.google.gson.JsonPrimitive(formatter.format(src));
        }

        @Override
        public LocalDateTime deserialize(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT, com.google.gson.JsonDeserializationContext context) throws com.google.gson.JsonParseException {
            return LocalDateTime.parse(json.getAsString(), formatter);
        }
    }


    public static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString()); // Serialize LocalDate as a string
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
            return LocalDate.parse(json.getAsString()); // Deserialize string to LocalDate
        }
    }
}
