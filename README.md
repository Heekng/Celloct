# Celloct [![Build Status](https://app.travis-ci.com/Heekng/Celloct.svg?token=WRCyztUpsDvwjVcyMsdU&branch=main)](https://app.travis-ci.com/Heekng/Celloct)

## 개요

- `Celloct`는 직원 근무시간 관리 및 재고관리 시스템이 없는 소상공인을 위한 프로젝트입니다.
- 현재 [CELLOCT](https://celloct.heekng.com) 에서 서비스하고있습니다.
- 기존 Java로 만들어진 서비스를 Kotlin 으로 수정중입니다!

## 구성

### Backend

- SpringBoot 2.6.4
- DataBase
  - real: postgreSQL
  - local: h2
- Spring Security
- Spring Data JPA
- Server: Ubuntu 22.04 ARM

### Frontend

- thymeleaf

## 개발 과정

- [x] DB(ERD) -> [CELLOCT ERD](https://www.erdcloud.com/d/MypHjcCYBskmfucBw)
  - 근무시간관리 테이블 작성 완료
  - 재고관리 테이블 추가 예정
- [x] Entity
- [x] Service, Repository
- [x] security, OAuth2
  - Google 로그인 구현 완료
  - Kakao 로그인 도입 예정
- [x] Controller
- [x] thymeleaf frontend
- [x] 서버 CI/CD 무중단 배포 자동화
  - [x] 배포 자동화
  - [x] 무중단 배포

## 현재 상태

- [x] Version1 - 기본 기능, 근무시간 관리 기능
  - [X] 로그인 기능
  - [X] 매장 생성
  - [X] 매장 가입
  - [X] 관리자-매장 홈
  - [X] 관리자-직원 관리
  - [X] 관리자-가입신청 관리
  - [x] 관리자-근무시간 관리
  - [X] 직원-매장 홈
  - [X] 직원-근무시간 조회
  - [X] 직원-근무시간 등록

