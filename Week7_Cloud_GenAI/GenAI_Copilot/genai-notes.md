# GenAI Fundamentals - My Notes

## what is generative AI

AI that can generate new content - text, images, code, audio

difference from traditional AI:
- traditional AI: classifies, predicts, detects (spam filter, image classification)
- generative AI: creates new content based on patterns learned from training data

### history
- 1960s: chatbots (ELIZA)
- 2014: GANs (Generative Adversarial Networks)
- 2017: Transformers architecture (attention is all you need paper)
- 2020: GPT-3 by OpenAI
- 2022: ChatGPT released
- 2023: GitHub Copilot, Claude, Gemini etc

### common GenAI applications
- text generation (ChatGPT, Claude)
- code generation (GitHub Copilot, Gemini)
- image generation (DALL-E, Midjourney, Stable Diffusion)
- chatbots and virtual assistants

---

## prompt engineering

the way you write your prompt completely changes the output

### zero-shot prompting
just ask directly without examples

```
"Write a Java method to reverse a string"
```

### few-shot prompting
give examples before the actual question

```
Example 1: Input: "hello" → Output: "olleh"
Example 2: Input: "java" → Output: "avaj"

Now do: Input: "spring"
```

### chain-of-thought prompting
ask the model to think step by step

```
"Solve this step by step: A train leaves at 9am..."
```

### best practices i noted
- be specific and clear
- give context (who you are, what you need it for)
- specify the format you want (json, bullet points, code)
- iterate - if output isn't right, refine the prompt
- avoid ambiguous words

### ethical considerations
- don't put personal data in prompts
- verify AI output before using it
- be aware of bias in AI responses
- don't use AI to generate misleading content

---

## GitHub Copilot

AI pair programmer built into VS Code (and other IDEs)

### how it works
- trained on public GitHub code
- suggests code as you type (ghost text)
- press Tab to accept, Esc to reject
- powered by OpenAI Codex

### what copilot can do
- complete functions from a comment
- generate boilerplate code
- suggest test cases
- explain existing code
- refactor code

### how to set it up
1. install GitHub Copilot extension in VS Code
2. sign in with GitHub account (need Copilot subscription or student plan)
3. open a Java file and start typing
4. suggestions appear as grey text

### example usage

```java
// write a method to check if a number is prime
// Copilot suggested:
public boolean isPrime(int n) {
    if (n <= 1) return false;
    for (int i = 2; i <= Math.sqrt(n); i++) {
        if (n % i == 0) return false;
    }
    return true;
}
```

### security and risks
- AI can suggest code with vulnerabilities
- can hallucinate (generate incorrect code that looks right)
- licensing issues - might suggest code from copyrighted sources
- don't commit sensitive data in comments (copilot sends context to server)
- always review suggestions before accepting

### responsible use
- treat suggestions as a starting point, not final code
- always understand what the code does before using it
- run tests on generated code
- don't rely on it for security-critical code without review

---

## Spring AI (bonus - explored on my own)

Spring framework has integration with AI models

```java
@RestController
public class AiController {

    @Autowired
    private ChatClient chatClient;

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return chatClient.call(question);
    }
}
```

application.properties:
```properties
spring.ai.openai.api-key=your-key-here
spring.ai.openai.model=gpt-3.5-turbo
```

interesting way to add AI features to a Spring Boot app
