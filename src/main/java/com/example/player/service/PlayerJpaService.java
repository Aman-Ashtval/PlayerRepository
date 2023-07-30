package com.example.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import com.example.player.model.Player;
import com.example.player.repository.*;

@Service
public class PlayerJpaService implements PlayerRepository{

    @Autowired 
    private PlayerJpaRepository db;

    @Override
    public ArrayList<Player> getPlayers(){
        List <Player> listOfPlayers = db.findAll();
        return new ArrayList<>(listOfPlayers);
    }

    @Override 
    public Player getPlayerById(int playerId){
        try{
            Player player = db.findById(playerId).get();
            return player;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override 
    public Player addPlayer(Player player){
        db.save(player);
        return player;
    }
    
    @Override 
    public Player updatePlayer(int playerId, Player player){
        try{
            Player oldPlayer = db.findById(playerId).get();

            if(player.getPlayerName() != null)
            oldPlayer.setPlayerName(player.getPlayerName());

            if((Integer)player.getJerseyNumber() != null)
            oldPlayer.setJerseyNumber(player.getJerseyNumber());

            if(player.getRole() != null)
            oldPlayer.setRole(player.getRole());

            db.save(oldPlayer);
            return oldPlayer;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override 
    public void deletePlayer(int playerId){
        try{
            db.deleteById(playerId);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}