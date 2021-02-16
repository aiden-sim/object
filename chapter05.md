# 책임 할당하기
- 데이터 중심 설계로 인해 발생하는 문제점을 해결할 수 있는 가장 기본적인 방법은 데이터가 아닌 책임에 초점을 맞추는 것
- 책임에 초점을 맞추는 설계에서 가장 어려움은 어떤 객체에게 어떤 책임을 할당할지를 결정하기 쉽지 않음
- GRASP 패턴을 통해 응집도와 결합도, 캡슐화 같은 다양한 기준에 따라 책임을 할당하고 결과를 트레이드오프할 수 있는 기준을 배우게됨


# 01 책임 주도 설계를 향해
- 데이터 중심의 설계에서 책임 중심의 설계로 전환하기 위해서 다음 두 가지 원칙을 따라야함
  - 데이터보다 행동을 먼저 결정하라
  - 협력이라는 문맥 안에서 책임을 결정하라
  
## 데이터보다 행동을 먼저 결정하라
- 클라이언트의 관점에서 객체가 수행하는 행동이란 곧 객체의 책임을 의미
- 데이터는 객체가 책임을 수행하는 데 필요한 재료를 제공할 뿐
- 우리에게 필요한 것은 객체의 데이터에서 행동으로 무게 중심을 옮기기 위한 기법
  - 책임 중심의 설계에서는 객체의 행동, 즉 책임을 먼저 결정한 후에 객체의 상태를 결정한다는 것

## 협력이라는 문맥 안에서 책임을 결정하라
- 객체에게 할당된 책임의 품질은 협력에 적합한 정도로 결정
  - 책임이 협력에 어울리지 않는다면 그 책임은 나쁨
  - 책임이 조금 어색해 보이더라도 협력에 적합하면 좋은 책임
  - 책임은 객체의 입장이 아니라 참여하는 협력에 적합해야 함
  
- 협력에 적합한 책임이란 메시지 수신자가 아닌 메시지 전송자에게 적합한 책임을 의미
  - 다시 말해 메시지를 전송하는 클라이언트의 의도에 적합한 책임을 할당해야 함

- 객체가 메시지를 선택하는 것이 아니라 메시지가 객체를 선택해야 함

```
클래스를 결정하고 그 클래스의 책임을 찾아 나서는 대신 메시지를 결정하고 메시지를 누구에게 전송할지 찾아보게 되었다.
"메시지를 전송해야 하는데 누구에게 전송해야 하지?" 라고 질문하는 것. 설계의 핵심 질문을 이렇게 바꾸는 것이 메시지 기반 설계로 향하는 첫걸음이다.
객체를 가지고 있기 때문에 메시지를 보내는 것이 아니다. 메시지를 전송하기 때문에 객체를 갖게 된 것이다.
```

- 클라이언트는 단지 임의의 객체가 메시지를 수신할 것이라는 사실을 믿고 자신의 의도를 표현할 메시지를 전송할 뿐
  - 메시지를 수신하기로 결정된 객체는 메시지를 처리할 "책임"을 할당받게 됨
- 메시지를 먼저 결정하기 때문에 메시지 송신자는 메시지 수신자에 대한 어떠한 가정도 할 수 없음
  - 메시지 전송자의 관점에서 메시지 수신자가 깔끔하게 캡슐화 됨
  
- 협력이라는 문맥에서 적절한 책임이란 곧 클라이언트의 관점에서 적절한 책임을 의미


## 책임 주도 설계
- 책임 주도 설계의 흐름을 다시 나열
  - 시스템이 사용자에게 제공해야 하는 기능인 시스템 책임을 파악한다.
  - 시스템 책임을 더 작은 책임으로 분활한다.
  - 분활된 책임을 수행할 수 있는 적절한 객체 또는 역할을 찾아 책임을 할당한다.
  - 객체가 책임을 수행하는 도중 다른 객체의 도움이 필요한 경우 이를 책임질 적절한 객체 또는 역할을 찾는다.
  - 해당 객체 또는 역할에게 책임을 할당함으로써 두 객체가 협력하게 된다.
  
- 책임 주도 설계의 핵심은 책임을 결정한 후에 책임을 수행할 객체를 결정하는 것
  - 협력에 참여하는 객체들의 책임이 어느 정도 정리될때까지 객체의 내부 상태에 대해 관심을 가지지 않는 것

# 02 책임 할당을 위한 GRASP 패턴
- GRASP은 (일반적인 책임 할당을 위한 소프트웨어 패턴) 의 약자로 객체에게 책임을 할당할 때 지침으로 삼을 수 있는 원칙들의 집합을 패턴 형식으로 정리한 것

## 도메인 개념에서 출발하기
- 설계를 시작하기 전에 도메인에 대한 개략적인 모습을 그려 보는 것이 유용
- 어떤 책임을 할당해야 할 때 가장 먼저 고민해야 하는 유력한 후보는 바로 도메인 개념


![5 1](https://user-images.githubusercontent.com/7076334/107771926-d7dfb000-6d7e-11eb-9325-d1df09a87f18.png)
- 해당 그림을 
통해


- 설계를 시작하는 단계에서는 개념들의 의미와 관계가 정확하거나 완벽할 필요가 없다. (단지 출발점이 필요할 뿐임)
- 중요한 것은 설계를 시작하는 것이지 도메인 개념들을 완벽하게 정리하는 것이 아니다.

### 올바른 도메인 모델이란 존재하지 않는다.


## 정보 전문까에게 책임을 할당하라
- 책임 주도 설계 방식의 첫 단계는 애플리케이션이 제공해야 하는 기능을 애플리케이션의 책임으로 생각하는 것
  - 이 책임을 전송된 메시지로 간주하고 이 메시지를 책임질 첫 번째 객체를 선택하는 것으로 설계 시작
  
- 첫 번째 질문
  - 메시지를 전송할 객체는 무엇을 원하는가?
    - 영화를 예매하는 것
- 두 번째 질문
  - 메시지를 수신할 적합한 객체는 누구인가?
    - 이 질문을 답하기 위해서 객체가 상태와 행동을 통합한 캡슐화의 단위라는 사실에 집중해야 됨
    - 객체에게 책임을 할당하는 첫 번째 원칙은 책임을 수행할 정보를 알고 있는 객체에게 책임을 할당하는 것 (정보 전문가 패턴)
    
### INFORMATION EXPERT 패턴
- 

- 정보 전문가가 데이터를 반드시 저장하고 있을 필요는 없다
- P140 ~ P142 예제들 좀더 정리
- 정보 전문가 패턴을 따르는 것만으로 자율성이 높은 객체들로 구성된 협력 공동체를 구축할 가능성이 높아짐

## 높은 응집도와 낮은 결합도
- 



    
    
  





