package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.Exception.DataException;
import org.example.model.Interest;
import org.example.service.InterestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class IntrestServlet extends HttpServlet {
    private static final long serialVersionUID=1L;

    private static final Logger log= LoggerFactory.getLogger(IntrestServlet.class);
    private final InterestService interestService=new InterestService();
    private final ObjectMapper objectMapper ;
    public IntrestServlet() {
        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
    }

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response){

        try{
            long accountId=Long.parseLong(request.getParameter("accountId"));

            List<Interest> interest =interestService.getInterestsByAccountId(accountId);
            ObjectMapper mapper=new ObjectMapper();
            String json=mapper.writeValueAsString(interest);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            response.getWriter().write(json);

        } catch (Exception e) {
            log.error("failed",e);
        }
    }

}