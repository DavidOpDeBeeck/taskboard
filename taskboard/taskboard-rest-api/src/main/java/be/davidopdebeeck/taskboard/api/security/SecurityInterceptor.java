package be.davidopdebeeck.taskboard.api.security;

import be.davidopdebeeck.taskboard.api.security.exception.TokenInvalidException;
import be.davidopdebeeck.taskboard.api.security.exception.TokenNotFoundException;
import be.davidopdebeeck.taskboard.core.Project;
import be.davidopdebeeck.taskboard.repository.LaneRepository;
import be.davidopdebeeck.taskboard.repository.ProjectRepository;
import be.davidopdebeeck.taskboard.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class SecurityInterceptor implements HandlerInterceptor
{

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    LaneRepository laneRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    SecurityManager securityManager;

    @Override
    public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler )
    {
        Map<String, String> pathVariables = (Map) request.getAttribute( HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE );

        if ( pathVariables != null && !request.getMethod().equals( "OPTIONS" ) )
        {
            String projectId = pathVariables.get( "projectId" );
            String laneId = pathVariables.get( "laneId" );
            String taskId = pathVariables.get( "taskId" );

            Project project = null;
            boolean secured = false;

            if ( projectId != null )
                project = projectRepository.findOne( projectId );
            else if ( laneId != null )
                project = laneRepository.findProject( laneRepository.findOne( laneId ) );
            else if ( taskId != null )
                project = taskRepository.findProject( taskRepository.findOne( taskId ) );

            if ( project != null )
                secured = project.isSecured();

            if ( secured )
            {
                Cookie[] cookies = request.getCookies();

                if ( cookies == null )
                    throw new TokenNotFoundException();

                boolean passwordValid = false, cookieFound = false;

                for ( Cookie cookie : cookies )
                {
                    if ( cookie.getName().equals( project.getId() ) )
                    {
                        String password = cookie.getValue();
                        cookieFound = true;

                        if ( securityManager.validate( project.getId(), password ) )
                        {
                            passwordValid = true;
                        }
                    }
                }

                if ( !cookieFound )
                    throw new TokenNotFoundException();

                if ( !passwordValid )
                    throw new TokenInvalidException();
            }

            return true;
        }

        return true;
    }

    @Override
    public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView ) throws Exception {}

    @Override
    public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex ) throws Exception {}
}
