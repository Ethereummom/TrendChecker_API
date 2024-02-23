package com.pingpong.jlab.pingpong.domain.strategy.controller;

import com.pingpong.jlab.pingpong.global.error.exception.ErrorCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.pingpong.jlab.pingpong.domain.strategy.dto.StrategyDTO;
import com.pingpong.jlab.pingpong.domain.strategy.service.StrategyService;
import com.pingpong.jlab.pingpong.global.api.ApiResponse;
import com.pingpong.jlab.pingpong.global.dto.PaginationRequestDto;
import com.pingpong.jlab.pingpong.global.jwt.JwtAuthentication;

import lombok.extern.log4j.Log4j2;

@Tag(name = "Strategy Controller", description = "투자 전략 데이터 출력 API")
@Log4j2
@RestController
@RequestMapping("/api/v1/strategy")
public class StrategyController {

    @Autowired
    StrategyService strategyService;



//    @GetMapping(value="")
//    public ApiResponse getStrategyList(PaginationRequestDto dto, @AuthenticationPrincipal JwtAuthentication userinfo){
////        strategyService.getStrategyList(dto.getCategory(),)
//        return null;
//    }

    @GetMapping("/detail")
    public ApiResponse getStrategyDetail(@RequestParam Long strategySeq, @RequestParam String symbol,
                                         @AuthenticationPrincipal JwtAuthentication userInfo){

        return strategyService.getStrategyDetail(strategySeq , symbol);
    }

    @GetMapping(value="/rank")
    public ApiResponse getStrategyTopFiveRank(@AuthenticationPrincipal JwtAuthentication userinfo, PaginationRequestDto dto){
        return strategyService.getStrategyTopFiveRank(dto);

    }

    @PostMapping(value = "")
    public ApiResponse addStrategy(@RequestBody StrategyDTO dto, @AuthenticationPrincipal JwtAuthentication userinfo){
        
        return strategyService.addStrategy(dto, userinfo.getUserid());
    }

    @PutMapping(value = "")
    public ApiResponse modifyStrategy(@RequestBody StrategyDTO dto,
                                      @AuthenticationPrincipal JwtAuthentication userinfo){
        return null;
    }

    @DeleteMapping(value = "/{strategySeq}")
    public ApiResponse deleteStrategy(@PathVariable("strategySeq")Long strategySeq){
        return strategyService.deleteStrategy(strategySeq);
    }

    @GetMapping(value="/daily")
    public ApiResponse getTodaysStrategy(@AuthenticationPrincipal JwtAuthentication userinfo){
        return strategyService.getSortedStrategy(2L);
    }

    @GetMapping("/finish")
    public ApiResponse finishStrategy(@RequestParam Long strategySeq,
                                      @AuthenticationPrincipal JwtAuthentication userinfo){
        return strategyService.finishStrategy(strategySeq);

    }

    @GetMapping("/recommends/{strategySeq}")
    public ApiResponse treatRecommend(@RequestParam("strategySeq")Long strategySeq ,@RequestParam int status,
                                      @AuthenticationPrincipal JwtAuthentication userinfo){
        if(status == 0){
            return strategyService.decreaseRecommend(strategySeq);
        }
        else if(status == 1){
            return strategyService.increaseRecommend(strategySeq);
        }
        return ApiResponse.res(400, ErrorCode.INVALID_INPUT_VALUE.getMessage());
    }

    @GetMapping("/subscribe")
    public ApiResponse subscribeStrategy(@RequestParam("strategySeq")Long strategySeq,@RequestParam int status,
                                        @AuthenticationPrincipal JwtAuthentication userinfo){
        if(status == 1){
            return strategyService.subscribeStrategy(strategySeq, userinfo.getUserid());
        }
        else{
            return strategyService.unSubscribeStrategy(strategySeq, userinfo.getUserid());
        }
    }
}
