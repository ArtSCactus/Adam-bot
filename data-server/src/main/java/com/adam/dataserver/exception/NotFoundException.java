package com.adam.dataserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ArtSCactus
 * @version 1.0
 */
@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "Requesting data is not found.")
public class NotFoundException extends RuntimeException  {
}
