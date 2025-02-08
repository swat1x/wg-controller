package ru.swat1x.wgcontroller.exception;

import org.springframework.http.HttpStatus;
import ru.swat1x.wgcontroller.exception.base.ResponseException;
import ru.swat1x.wgcontroller.exception.base.ServiceException;

import java.util.UUID;

@ResponseException(HttpStatus.NOT_FOUND)
public class PeerNotFoundException extends ServiceException {

    public PeerNotFoundException(UUID peerId) {
        super("Peer not found: " + peerId);
    }
}
