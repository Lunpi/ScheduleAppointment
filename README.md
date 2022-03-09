# ScheduleAppointment

Since I can't access to your API, I created a dummy URL and a dummy request.
You probably need to modify the URL and the request to fit the actual API.
Please check the following variables and modify them if needed:

ScheduleRepository.kt:
    - BASE_URL: modify it to the actual URL.
    - HTTP_HEADER_KEY, HTTP_HEADER_VALUE: if some headers are needed to access to your server.

ApiService.kt:
    - @GET(<value>): modify <value> to API URL.
    - @Query(<parameter>): modify <parameter> to the corresponding parameter in the actual request.
          I assumed the parameter is timestamp of Sunday 00:00 AM of the target week,
          and server would response all time slots (available & booked) during the target week.

Please feel free to contact me if you encounter any problem or have any question. Thanks!
